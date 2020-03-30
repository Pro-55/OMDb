package com.example.omdb.data.contract

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.omdb.models.FullData
import com.example.omdb.models.Resource
import com.example.omdb.models.SearchResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface HomeRepository {

    fun searchMovies(searchString: String,page: Int): LiveData<Resource<SearchResult>>

    fun getMovieDetails(id: String): LiveData<Resource<FullData>>

}