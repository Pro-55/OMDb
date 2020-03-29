package com.example.omdb.data.contract

import androidx.lifecycle.LiveData
import com.example.omdb.models.FullData
import com.example.omdb.models.SearchResult

interface HomeRepository {

    fun searchMovies(searchString: String,page: Int): LiveData<SearchResult>

    fun getMovieDetails(id: String): LiveData<FullData>

}