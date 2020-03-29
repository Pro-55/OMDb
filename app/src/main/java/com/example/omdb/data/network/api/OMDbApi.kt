package com.example.omdb.data.network.api

import androidx.lifecycle.LiveData
import com.example.omdb.models.FullData
import com.example.omdb.models.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDbApi {

    @GET
    fun searchMovies(@Query("apikey") apiKey: String,
                     @Query("s") movieTitle: String,
                     @Query("page") page: Int): LiveData<SearchResult>

    @GET
    fun getMovieDetails(@Query("apikey") apiKey: String,
                        @Query("i") movieID: String?): LiveData<FullData>

}