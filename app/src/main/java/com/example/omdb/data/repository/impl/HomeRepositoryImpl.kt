package com.example.omdb.data.repository.impl

import androidx.room.withTransaction
import com.example.omdb.BuildConfig.ApiKey
import com.example.omdb.data.api.OMDbApi
import com.example.omdb.data.local.AppDatabase
import com.example.omdb.data.repository.contract.HomeRepository
import com.example.omdb.models.*
import com.example.omdb.models.local.*
import com.example.omdb.models.network.parse
import com.example.omdb.util.Constants
import com.example.omdb.util.extensions.resourceFlow
import kotlinx.coroutines.flow.Flow
import java.net.UnknownHostException

class HomeRepositoryImpl constructor(
    private val api: OMDbApi,
    private val db: AppDatabase
) : HomeRepository {

    // Global
    private val TAG = HomeRepositoryImpl::class.java.simpleName

    override fun signUp(user: EntityUser): Flow<Resource<User>> {
        return resourceFlow {
            val insertResult = db.userDao.insert(user)
            if (insertResult > -1) {
                emit(Resource.success(user.parse()))
            } else {
                emit(Resource.error(Constants.REQUEST_FAILED_MESSAGE))
            }
        }
    }

    override fun searchMovies(searchString: String, page: Int): Flow<Resource<SearchResult>> {
        return resourceFlow {
            val result = api.searchContent(
                apiKey = ApiKey,
                title = searchString,
                type = "movie",
                page = page
            )

            if (result.isSuccessful) {
                val data = result.body()?.parse() ?: SearchResult()
                emit(Resource.success(data))
            } else {
                val msg = result.message()
                emit(Resource.error(msg))
            }
        }
    }

    override fun searchSeries(searchString: String, page: Int): Flow<Resource<SearchResult>> {
        return resourceFlow {
            val result = api.searchContent(
                apiKey = ApiKey,
                title = searchString,
                type = "series",
                page = page
            )

            if (result.isSuccessful) {
                val data = result.body()?.parse() ?: SearchResult()
                emit(Resource.success(data))
            } else {
                val msg = result.message()
                emit(Resource.error(msg))
            }
        }
    }

    override fun getDetails(id: String, plot: String): Flow<Resource<FullData>> {
        return resourceFlow {
            val relation = db.dataDao.get(id)

            val result = try {
                api.getDetails(ApiKey, id, plot)
            } catch (e: Exception) {
                null
            }

            if (result?.isSuccessful == true) {
                val network = result.body()
                if (network != null) {
                    val isFavorite = relation?.data?.isFavorite ?: false
                    val data = network.parse(isFavorite)
                    val ratings = network.ratings.parse(id)

                    val insertResult = db.withTransaction {
                        val dataResult = db.dataDao.insert(data)
                        val ratingsResult = db.ratingDao.insertAll(ratings)
                        return@withTransaction dataResult * ratingsResult
                            .reduce { t, e -> t * e }
                    }

                    when {
                        insertResult > -1 -> emit(Resource.success(data.parse(ratings)))
                        relation != null -> emit(Resource.success(relation.parse()))
                        else -> emit(Resource.error(Constants.REQUEST_FAILED_MESSAGE))
                    }
                    return@resourceFlow
                }
            }

            when {
                relation != null -> emit(Resource.success(relation.parse()))
                else -> emit(Resource.error(Constants.REQUEST_FAILED_MESSAGE))
            }
        }
    }

    override fun getEpisodes(id: String, season: Int): Flow<Resource<Season>> {
        return resourceFlow {
            val local = db.episodeDao.get(id, season)

            val result = try {
                api.getEpisodes(ApiKey, id, season)
            } catch (e: UnknownHostException) {
                null
            }

            if (result?.isSuccessful == true) {
                val network = result.body()
                if (network != null) {
                    val episodes = network.episodes.parse(id, season)
                    val insertResult = db.episodeDao.insertAll(episodes)
                        .reduce { t, e -> t * e }

                    when {
                        insertResult > -1 -> emit(Resource.success(Season(episodes = episodes.parse())))
                        local != null -> emit(Resource.success(Season(episodes = local.parse())))
                        else -> emit(Resource.error(Constants.REQUEST_FAILED_MESSAGE))
                    }
                    return@resourceFlow
                }
            }

            when {
                local != null -> emit(Resource.success(Season(episodes = local.parse())))
                else -> emit(Resource.error(Constants.REQUEST_FAILED_MESSAGE))
            }
        }
    }
}