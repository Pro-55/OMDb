package com.example.omdb.listmodule

import android.app.ProgressDialog
import androidx.appcompat.widget.AppCompatImageView
import com.example.omdb.network.responce.Movie
import com.example.omdb.network.responce.SearchData

class ListPresenter(iListPresenter: IListPresenter) : IListPresenterImpl {

    private var listPresenterImpl: ListPresenterImpl? = null
    private var listPresenterInterface: IListPresenter? = null
    private var page = 1

    init {
        listPresenterInterface = iListPresenter
        listPresenterImpl = ListPresenterImpl(this)
    }

    fun getSearchResult(searchBoxText: String, loader: ProgressDialog) {
        page = 1
        listPresenterImpl!!.callSearchApi(searchBoxText, page, loader)
    }

    fun getMoreResults(searchBoxText: String, loader: ProgressDialog) {
        listPresenterImpl!!.callSearchApi(searchBoxText, page, loader)
    }

    fun getMovieDetails(imdbID: String?, targetView: AppCompatImageView, loader: ProgressDialog) {
        listPresenterImpl!!.callMovieDetailsApi(imdbID, targetView, loader)
    }

    override fun successSearchResult(
        searchData: List<SearchData>?,
        totalResults: String?,
        loader: ProgressDialog
    ) {
        page += 1
        listPresenterInterface!!.successSearch(searchData, totalResults, loader)
    }

    override fun failSearchResult(error: String?, loader: ProgressDialog) {
        if (page > 1) {
            page -= 1
        }
        listPresenterInterface!!.failSearch(error, loader)
    }

    override fun successMovieDetails(
        movie: Movie,
        targetView: AppCompatImageView,
        loader: ProgressDialog
    ) {
        listPresenterInterface!!.successGetDetails(movie, targetView, loader)
    }

    override fun failMovieDetails(error: String?, loader: ProgressDialog) {
        listPresenterInterface!!.failGetDetails(error, loader)
    }
}



