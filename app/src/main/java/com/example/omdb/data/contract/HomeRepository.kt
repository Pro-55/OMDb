package com.example.omdb.data.contract

import com.example.omdb.models.FullData
import com.example.omdb.models.Resource
import com.example.omdb.models.SearchResult
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun searchMovies(searchString: String, page: Int): Flow<Resource<SearchResult>>

    fun searchSeries(searchString: String, page: Int): Flow<Resource<SearchResult>>

    fun searchEpisodes(searchString: String, page: Int): Flow<Resource<SearchResult>>

    fun getMovieDetails(id: String, plot: String): Flow<Resource<FullData>>

}