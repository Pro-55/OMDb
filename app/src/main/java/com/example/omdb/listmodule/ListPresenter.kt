package com.example.omdb.listmodule

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

    fun getSearchResult(searchBoxText: String) {
        page = 1
        listPresenterImpl!!.callSearchApi(searchBoxText, page)
    }

    fun getMoreResults(searchBoxText: String) {
        listPresenterImpl!!.callSearchApi(searchBoxText, page)
    }

    fun getMovieDetails(imdbID: String?, targetView: AppCompatImageView) {
        listPresenterImpl!!.callMovieDetailsApi(imdbID, targetView)
    }

    override fun successSearchResult(
        searchData: List<SearchData>?,
        totalResults: String?
    ) {
        page += 1
        listPresenterInterface!!.successSearch(searchData, totalResults)
    }

    override fun failSearchResult(error: String?) {
        if (page > 1) {
            page -= 1
        }
        listPresenterInterface!!.failSearch(error)
    }

    override fun successMovieDetails(movie: Movie, targetView: AppCompatImageView) {
        listPresenterInterface!!.successGetDetails(movie, targetView)
    }

    override fun failMovieDetails(error: String?) {
        listPresenterInterface!!.failGetDetails(error)
    }
}



