package com.example.omdb.listmodule

import android.app.ProgressDialog
import androidx.appcompat.widget.AppCompatImageView
import com.example.omdb.network.responce.Movie
import com.example.omdb.network.responce.SearchData

interface IListPresenterImpl {
    fun successSearchResult(
        searchData: List<SearchData>?,
        totalResults: String?,
        loader: ProgressDialog
    )

    fun successMovieDetails(
        movie: Movie,
        targetView: AppCompatImageView,
        loader: ProgressDialog
    )

    fun failSearchResult(error: String?, loader: ProgressDialog)

    fun failMovieDetails(error: String?, loader: ProgressDialog)
}
