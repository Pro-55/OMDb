package com.example.omdb.data.network.api

import com.example.omdb.models.FullData
import com.example.omdb.models.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDbApi {

    @GET("/")
    fun searchMovies(@Query("apikey") apiKey: String,
                     @Query("s") movieTitle: String,
                     @Query("page") page: Int): Call<SearchResult>

    @GET("/")
    fun getMovieDetails(@Query("apikey") apiKey: String,
                        @Query("i") movieID: String?): Call<FullData>

}