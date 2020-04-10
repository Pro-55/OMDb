package com.example.omdb.data.repository.contract

import com.example.omdb.models.*
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun signUp(user: User)

    fun searchMovies(searchString: String, page: Int): Flow<Resource<SearchResult>>

    fun searchSeries(searchString: String, page: Int): Flow<Resource<SearchResult>>

    fun getDetails(id: String, plot: String): Flow<Resource<FullData>>

    fun getEpisodes(id: String, season: Int): Flow<Resource<Season>>
}