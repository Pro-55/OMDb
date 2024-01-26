package com.example.omdb.data.repository

import android.content.SharedPreferences
import androidx.room.withTransaction
import com.example.omdb.data.local.AppDatabase
import com.example.omdb.data.local.model.EntityUser
import com.example.omdb.data.local.model.parse
import com.example.omdb.data.network.api.contract.OMDbApi
import com.example.omdb.data.network.model.Response
import com.example.omdb.data.network.model.parse
import com.example.omdb.domain.model.Content
import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.model.SearchResult
import com.example.omdb.domain.model.Season
import com.example.omdb.domain.model.Type
import com.example.omdb.domain.model.User
import com.example.omdb.domain.repository.MainRepository
import com.example.omdb.util.Constants
import com.example.omdb.util.extensions.isSuccessful
import com.example.omdb.util.wrappers.resourceFlow
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.UUID

class MainRepositoryImpl(
    private val api: OMDbApi,
    private val db: AppDatabase,
    private val sp: SharedPreferences,
    private val auth: FirebaseAuth
) : MainRepository {

    // Global
    private val TAG = MainRepositoryImpl::class.java.simpleName

    override fun fetchFirebaseUser(
        credential: AuthCredential
    ): Flow<Resource<FirebaseUser>> = resourceFlow {
        val user = auth.signInWithCredential(credential)
            .await()
            ?.user

        if (user == null) {
            emit(Resource.Error(msg = Constants.ERROR_MESSAGE_UNKNOWN))
            return@resourceFlow
        }

        emit(Resource.Success(data = user))
    }

    override fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        profileUrl: String?
    ): Flow<Resource<User>> = resourceFlow {
        val user = EntityUser(
            _id = auth.currentUser?.uid ?: UUID.randomUUID().toString(),
            firstName = firstName,
            lastName = lastName,
            email = email,
            profileUrl = profileUrl
        )
        val insertResult = db.userDao.insert(user)
        if (insertResult > -1) {
            with(sp.edit()) {
                putBoolean(Constants.KEY_SIGN_UP_STATUS, true)
                putString(Constants.KEY_USER_ID, user._id)
                apply()
            }
            emit(Resource.Success(user.parse()))
        } else {
            emit(Resource.Error(Constants.REQUEST_FAILED_MESSAGE))
        }
    }

    override fun getCurrentUser(): Flow<Resource<User>> = resourceFlow {
        val userId = sp.getString(Constants.KEY_USER_ID, null)
        if (userId.isNullOrEmpty()) {
            emit(Resource.Error("Invalid User ID!"))
            return@resourceFlow
        }

        val user = db.userDao.get(userId)
        if (user == null) {
            emit(Resource.Error("User Not Found!"))
            return@resourceFlow
        }

        emit(Resource.Success(user.parse()))
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
                emit(Resource.Success(data))
            }
            is Response.UnknownHostException -> {
                val search =
                    db.shortContentDao.searchForType(type, "$searchString%")?.parse() ?: listOf()
                val data = SearchResult(search = search, totalResults = search.size.toString())
                emit(Resource.Success(data))
            }
            else -> {
                val msg = result.msg ?: Constants.REQUEST_FAILED_MESSAGE
                emit(Resource.Error(msg))
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
                        emit(Resource.Success(data.parse(ratings)))
                        return@resourceFlow
                    }
                }
                null
            }
            is Response.UnknownHostException -> {
                if (local != null) {
                    emit(Resource.Success(local.parse()))
                    return@resourceFlow
                }
                null
            }
            else -> result.msg
        }
        emit(Resource.Error(msg ?: Constants.REQUEST_FAILED_MESSAGE))
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
                        emit(Resource.Success(Season(episodes = episodes.parse())))
                        return@resourceFlow
                    }
                }
                null
            }
            is Response.UnknownHostException -> {
                val local = db.episodeDao.get(id, season)
                if (!local.isNullOrEmpty()) {
                    emit(Resource.Success(Season(episodes = local.parse())))
                    return@resourceFlow
                }
                null
            }
            else -> result.msg
        }
        emit(Resource.Error(msg ?: Constants.REQUEST_FAILED_MESSAGE))
    }
}