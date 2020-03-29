package com.example.omdb.data.repository

import androidx.lifecycle.LiveData
import com.example.omdb.BuildConfig.ApiKey
import com.example.omdb.data.contract.HomeRepository
import com.example.omdb.data.network.api.OMDbApi
import com.example.omdb.models.FullData
import com.example.omdb.models.SearchResult
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: OMDbApi
) : HomeRepository {

    override fun searchMovies(searchString: String, page: Int): LiveData<SearchResult> {
        return api.searchMovies(ApiKey, searchString, page)
    }

    override fun getMovieDetails(id: String): LiveData<FullData> {
        return api.getMovieDetails(ApiKey, id)
    }

}