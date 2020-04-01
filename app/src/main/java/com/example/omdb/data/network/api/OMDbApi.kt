package com.example.omdb.data.network.api

import com.example.omdb.models.FullData
import com.example.omdb.models.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDbApi {

    @GET("/")
    fun searchContent(
        @Query("apikey") apiKey: String,
        @Query("s") title: String,
        @Query("type") type: String,
        @Query("page") page: Int
    ): Call<SearchResult>

    @GET("/")
    fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("i") id: String
    ): Call<FullData>

}