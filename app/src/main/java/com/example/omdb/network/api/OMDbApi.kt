package com.example.omdb.network.api

import com.example.omdb.network.responce.Movie
import com.example.omdb.network.responce.SearchResult

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface OMDbApi {

    @GET("/")
    fun searchMovies(@Query("apikey") apiKey: String,
                     @Query("s") movieTitle: String,
                     @Query("page") page: Int): Observable<SearchResult>

    @GET("/")
    fun getMovieDetails(@Query("apikey") apiKey: String,
                        @Query("i") movieID: String?): Observable<Movie>

}