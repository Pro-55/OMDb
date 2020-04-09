package com.example.omdb.data.contract

import com.example.omdb.models.FullData
import com.example.omdb.models.Resource
import com.example.omdb.models.SearchResult
import com.example.omdb.models.Season
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun searchMovies(searchString: String, page: Int): Flow<Resource<SearchResult>>

    fun searchSeries(searchString: String, page: Int): Flow<Resource<SearchResult>>

    fun getDetails(id: String, plot: String): Flow<Resource<FullData>>

    fun getEpisodes(id: String, season: Int): Flow<Resource<Season>>
}