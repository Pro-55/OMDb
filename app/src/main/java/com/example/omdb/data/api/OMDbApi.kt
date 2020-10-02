package com.example.omdb.data.api

import com.example.omdb.models.FullData
import com.example.omdb.models.SearchResult
import com.example.omdb.models.Season
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDbApi {

    @GET("/")
    suspend fun searchContent(
        @Query("apikey") apiKey: String,
        @Query("s") title: String,
        @Query("type") type: String,
        @Query("page") page: Int
    ): Response<SearchResult>

    @GET("/")
    suspend fun getDetails(
        @Query("apikey") apiKey: String,
        @Query("i") id: String,
        @Query("plot") plot: String
    ): Response<FullData>

    @GET("/")
    suspend fun getEpisodes(
        @Query("apikey") apiKey: String,
        @Query("i") id: String,
        @Query("season") season: Int
    ): Response<Season>

}