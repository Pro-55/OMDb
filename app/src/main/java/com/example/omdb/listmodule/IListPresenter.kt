package com.example.omdb.listmodule

import android.app.ProgressDialog
import androidx.appcompat.widget.AppCompatImageView
import com.example.omdb.network.responce.Movie
import com.example.omdb.network.responce.SearchData

interface IListPresenter {
    fun successSearch(
        searchData: List<SearchData>?,
        totalResults: String?,
        loader: ProgressDialog
    )

    fun successGetDetails(
        movie: Movie,
        targetView: AppCompatImageView,
        loader: ProgressDialog
    )

    fun failSearch(error: String?, loader: ProgressDialog)

    fun failGetDetails(error: String?, loader: ProgressDialog)
}
