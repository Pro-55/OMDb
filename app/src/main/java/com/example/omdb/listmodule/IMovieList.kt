package com.example.omdb.listmodule

import androidx.appcompat.widget.AppCompatImageView
import com.example.omdb.network.responce.SearchData

interface IMovieList {
    fun showMovieDetails(searchData: SearchData, targetView: AppCompatImageView)
}
