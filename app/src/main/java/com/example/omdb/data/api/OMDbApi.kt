package com.example.omdb.data.api

import com.example.omdb.models.network.NetworkContent
import com.example.omdb.models.network.NetworkSearchResult
import com.example.omdb.models.network.NetworkSeason
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
    ): Response<NetworkSearchResult>

    @GET("/")
    suspend fun getDetails(
        @Query("apikey") apiKey: String,
        @Query("i") id: String,
        @Query("plot") plot: String
    ): Response<NetworkContent>

    @GET("/")
    suspend fun getEpisodes(
        @Query("apikey") apiKey: String,
        @Query("i") id: String,
        @Query("season") season: Int
    ): Response<NetworkSeason>

}