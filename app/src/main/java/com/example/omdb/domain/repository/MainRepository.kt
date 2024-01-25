package com.example.omdb.data.repository.contract

import com.example.omdb.data.local.model.EntityUser
import com.example.omdb.domain.model.Content
import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.model.SearchResult
import com.example.omdb.domain.model.Season
import com.example.omdb.domain.model.Type
import com.example.omdb.domain.model.User
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun signUp(user: EntityUser): Flow<Resource<User>>

    fun getCurrentUser(): Flow<Resource<User>>

    fun searchContent(searchString: String, page: Int, type: Type): Flow<Resource<SearchResult>>

    fun getDetails(id: String, plot: String): Flow<Resource<Content>>

    fun getEpisodes(id: String, season: Int): Flow<Resource<Season>>
}