package com.example.omdb.data.contract

import androidx.lifecycle.LiveData
import com.example.omdb.models.FullData
import com.example.omdb.models.Resource
import com.example.omdb.models.SearchResult

interface HomeRepository {

    fun searchMovies(searchString: String, page: Int): LiveData<Resource<SearchResult>>

    fun searchSeries(searchString: String, page: Int): LiveData<Resource<SearchResult>>

    fun searchEpisodes(searchString: String, page: Int): LiveData<Resource<SearchResult>>

    fun getMovieDetails(id: String): LiveData<Resource<FullData>>

}