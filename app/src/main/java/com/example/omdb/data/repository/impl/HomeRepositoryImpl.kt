package com.example.omdb.data.repository.impl

import android.content.SharedPreferences
import androidx.room.withTransaction
import com.example.omdb.data.api.contract.OMDbApi
import com.example.omdb.data.local.AppDatabase
import com.example.omdb.data.repository.contract.HomeRepository
import com.example.omdb.models.*
import com.example.omdb.models.local.EntityUser
import com.example.omdb.models.local.parse
import com.example.omdb.models.network.Response
import com.example.omdb.models.network.parse
import com.example.omdb.util.Constants
import com.example.omdb.util.extensions.isSuccessful
import com.example.omdb.util.wrappers.resourceFlow
import kotlinx.coroutines.flow.Flow

class HomeRepositoryImpl constructor(
    private val api: OMDbApi,
    private val db: AppDatabase,
    private val sp: SharedPreferences
) : HomeRepository {

    // Global
    private val TAG = HomeRepositoryImpl::class.java.simpleName

    override fun signUp(user: EntityUser): Flow<Resource<User>> = resourceFlow {
        val insertResult = db.userDao.insert(user)
        if (insertResult > -1) {
            with(sp.edit()) {
                putBoolean(Constants.KEY_SIGN_UP_STATUS, true)
                putString(Constants.KEY_USER_ID, user._id)
                apply()
            }
            emit(Resource.success(user.parse()))
        } else {
            emit(Resource.error(Constants.REQUEST_FAILED_MESSAGE))
        }
    }

    override fun getCurrentUser(): Flow<Resource<User>> = resourceFlow {
        val userId = sp.getString(Constants.KEY_USER_ID, null)
        if (userId.isNullOrEmpty()) {
            emit(Resource.error("Invalid User ID!"))
            return@resourceFlow
        }

        val user = db.userDao.get(userId)
        if (user == null) {
            emit(Resource.error("User Not Found!"))
            return@resourceFlow
        }

        emit(Resource.success(user.parse()))
    }

    override fun searchContent(
        searchString: String,
        page: Int,
        type: Type
    ): Flow<Resource<SearchResult>> = resourceFlow {
        val result = api.searchContent(
            title = searchString,
            page = page,
            type = type
        )
        when (result) {
            is Response.Success -> {
                val body = result.data
                val search = body?.search?.parse(type) ?: listOf()
                db.shortContentDao.insertAll(search)
                val data = body?.parse(search) ?: SearchResult()
                emit(Resource.success(data))
            }
            is Response.UnknownHostException -> {
                val search =
                    db.shortContentDao.searchForType(type, "$searchString%")?.parse() ?: listOf()
                val data = SearchResult(search = search, totalResults = search.size.toString())
                emit(Resource.success(data))
            }
            else -> {
                val msg = result.msg ?: Constants.REQUEST_FAILED_MESSAGE
                emit(Resource.error(msg))
            }
        }
    }

    override fun getDetails(
        id: String,
        plot: String
    ): Flow<Resource<Content>> = resourceFlow {
        val local = db.contentDao.get(id)

        val result = api.getDetails(
            id = id,
            plot = plot
        )

        val msg = when (result) {
            is Response.Success -> {
                val network = result.data
                if (network != null) {
                    val isFavorite = local?.data?.isFavorite ?: false
                    val data = network.parse(isFavorite)
                    val ratings = network.ratings?.parse(id) ?: listOf()

                    val dbSuccess = db.withTransaction {
                        val dataResult = db.contentDao.insert(data).isSuccessful()
                        val ratingsResult = db.ratingDao.insertAll(ratings).isSuccessful()
                        return@withTransaction dataResult && ratingsResult
                    }

                    if (dbSuccess) {
                        emit(Resource.success(data.parse(ratings)))
                        return@resourceFlow
                    }
                }
                null
            }
            is Response.UnknownHostException -> {
                if (local != null) {
                    emit(Resource.success(local.parse()))
                    return@resourceFlow
                }
                null
            }
            else -> result.msg
        }
        emit(Resource.error(msg ?: Constants.REQUEST_FAILED_MESSAGE))
    }

    override fun getEpisodes(
        id: String,
        season: Int
    ): Flow<Resource<Season>> = resourceFlow {
        val result = api.getEpisodes(
            id = id,
            season = season
        )

        val msg = when (result) {
            is Response.Success -> {
                val network = result.data
                if (network != null) {
                    val episodes = network.episodes?.parse(id, season) ?: listOf()
                    val dbSuccess = db.episodeDao.insertAll(episodes).isSuccessful()
                    if (dbSuccess) {
                        emit(Resource.success(Season(episodes = episodes.parse())))
                        return@resourceFlow
                    }
                }
                null
            }
            is Response.UnknownHostException -> {
                val local = db.episodeDao.get(id, season)
                if (!local.isNullOrEmpty()) {
                    emit(Resource.success(Season(episodes = local.parse())))
                    return@resourceFlow
                }
                null
            }
            else -> result.msg
        }
        emit(Resource.error(msg ?: Constants.REQUEST_FAILED_MESSAGE))
    }
}