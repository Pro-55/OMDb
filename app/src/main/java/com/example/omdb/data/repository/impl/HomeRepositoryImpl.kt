package com.example.omdb.data.repository.impl

import android.content.SharedPreferences
import androidx.room.withTransaction
import com.example.omdb.BuildConfig.ApiKey
import com.example.omdb.data.api.OMDbApi
import com.example.omdb.data.local.AppDatabase
import com.example.omdb.data.repository.contract.HomeRepository
import com.example.omdb.models.*
import com.example.omdb.models.local.*
import com.example.omdb.models.network.parse
import com.example.omdb.util.Constants
import com.example.omdb.util.extensions.isSuccessful
import com.example.omdb.util.extensions.resourceFlow
import com.google.firebase.crashlytics.FirebaseCrashlytics
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
        val result = try {
            api.searchContent(
                apiKey = ApiKey,
                title = searchString,
                type = type.toString(),
                page = page
            )
        } catch (e: Exception) {
            e.printStackTrace()
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }

        when {
            result == null -> {
                val search = db.contentDao.searchForType(type, "$searchString%")
                    ?.parse()?.toShortData() ?: listOf()
                val data = SearchResult(search = search, totalResults = search.size.toString())
                emit(Resource.success(data))
            }
            result.isSuccessful -> {
                val body = result.body()
                val search = body?.search?.parse() ?: listOf()
                db.contentDao.insert(search)
                val data = body?.parse(search) ?: SearchResult()
                emit(Resource.success(data))
            }
            else -> {
                val msg = result.message() ?: Constants.REQUEST_FAILED_MESSAGE
                emit(Resource.error(msg))
            }
        }
    }

    override fun getDetails(
        id: String,
        plot: String
    ): Flow<Resource<Content>> = resourceFlow {
        var isSuccess = false
        val local = db.contentDao.get(id)
        if (local != null) {
            isSuccess = true
            emit(Resource.success(local.parse()))
        }

        val result = try {
            api.getDetails(
                apiKey = ApiKey,
                id = id,
                plot = plot
            )
        } catch (e: Exception) {
            e.printStackTrace()
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }

        if (result?.isSuccessful == true) {
            val network = result.body()
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
                    if (!isSuccess) isSuccess = true
                    emit(Resource.success(data.parse(ratings)))
                }
            }
        }

        if (!isSuccess) {
            val msg = result?.message() ?: Constants.REQUEST_FAILED_MESSAGE
            emit(Resource.error(msg))
        }
    }

    override fun getEpisodes(
        id: String,
        season: Int
    ): Flow<Resource<Season>> = resourceFlow {
        var isSuccess = false
        val local = db.episodeDao.get(id, season)
        if (local != null) {
            isSuccess = true
            emit(Resource.success(Season(episodes = local.parse())))
        }

        val result = try {
            api.getEpisodes(
                apiKey = ApiKey,
                id = id,
                season = season
            )
        } catch (e: Exception) {
            e.printStackTrace()
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }

        if (result?.isSuccessful == true) {
            val network = result.body()
            if (network != null) {
                val episodes = network.episodes?.parse(id, season) ?: listOf()
                val dbSuccess = db.episodeDao.insertAll(episodes).isSuccessful()
                if (dbSuccess) {
                    if (!isSuccess) isSuccess = true
                    emit(Resource.success(Season(episodes = episodes.parse())))
                }
            }
        }

        if (!isSuccess) {
            val msg = result?.message() ?: Constants.REQUEST_FAILED_MESSAGE
            emit(Resource.error(msg))
        }
    }
}