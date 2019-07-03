package com.example.omdb.listmodule

import androidx.appcompat.widget.AppCompatImageView
import com.example.omdb.network.responce.Movie
import com.example.omdb.network.responce.SearchData

interface IListPresenterImpl {
    fun successSearchResult(searchData: List<SearchData>?)

    fun successMovieDetails(movie: Movie, targetView: AppCompatImageView)

    fun failSearchResult(error: String?)

    fun failMovieDetails(error: String?)
}
