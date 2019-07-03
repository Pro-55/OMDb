package com.example.omdb.listmodule

import androidx.appcompat.widget.AppCompatImageView
import com.example.omdb.network.responce.Movie
import com.example.omdb.network.responce.SearchData

interface IListPresenter {
    fun successSearch(searchData: List<SearchData>?)

    fun successGetDetails(movie: Movie, targetView: AppCompatImageView)

    fun failSearch(error: String?)

    fun failGetDetails(error: String?)
}
