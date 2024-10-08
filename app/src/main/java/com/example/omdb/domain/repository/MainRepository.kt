package com.example.omdb.domain.repository

import com.example.omdb.domain.model.Content
import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.model.SearchResult
import com.example.omdb.domain.model.Season
import com.example.omdb.domain.model.Type
import com.example.omdb.domain.model.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface MainRepository {

    fun getSignUpStatus(): Flow<Resource<Boolean>>

    fun fetchFirebaseUser(credential: AuthCredential?): Flow<Resource<FirebaseUser>>

    fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        profileUrl: String?
    ): Flow<Resource<User>>

    fun getCurrentUser(): Flow<Resource<User>>

    suspend fun getGreeting(
        userName: String?,
        calendar: Calendar
    ): String

    fun searchContent(
        query: String,
        page: Int,
        type: Type
    ): Flow<Resource<SearchResult>>

    fun getDetails(
        id: String,
        plot: String
    ): Flow<Resource<Content>>

    fun getEpisodes(
        id: String,
        season: Int
    ): Flow<Resource<Season>>
}