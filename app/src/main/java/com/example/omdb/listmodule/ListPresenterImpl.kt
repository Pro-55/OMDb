package com.example.omdb.listmodule

import androidx.appcompat.widget.AppCompatImageView
import com.example.omdb.network.ApiFunctions.ApiFail
import com.example.omdb.network.ApiFunctions.ApiSuccess
import com.example.omdb.network.ApiFunctions.HttpErrorResponse
import com.example.omdb.network.api.Api
import com.example.omdb.network.responce.Movie
import com.example.omdb.network.responce.SearchResult
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ListPresenterImpl(iListPresenterImpl: IListPresenterImpl) {

    //var
    private var listPresenterImplInterface: IListPresenterImpl? = null

    //val
    private val apiKey = "b0b9b72c"
    private val TAG = "ListPresenterImpl"

    init {
        listPresenterImplInterface = iListPresenterImpl
    }

    fun callSearchApi(searchBoxText: String, page: Int) {
        Api.OMDbApi().searchMovies(apiKey, searchBoxText, page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : ApiSuccess<SearchResult>() {
                override fun call(searchResult: SearchResult) {
                    if (searchResult.response == "True") {
                        listPresenterImplInterface!!.successSearchResult(
                            searchResult.searchData,
                            searchResult.totalResults
                        )
                    } else if (searchResult.response == "False") {
                        listPresenterImplInterface!!.failSearchResult(searchResult.error)
                    }
                }
            }, object : ApiFail() {
                override fun httpStatus(response: HttpErrorResponse) {
                    listPresenterImplInterface!!.failSearchResult(response.error)
                }

                override fun noNetworkError() {
                    listPresenterImplInterface!!.failSearchResult("Please Check Your Internet")
                }

                override fun unknownError(throwable: Throwable) {
                    listPresenterImplInterface!!.failSearchResult(throwable.message)
                }
            })
    }

    fun callMovieDetailsApi(imdbID: String?, targetView: AppCompatImageView) {
        Api.OMDbApi().getMovieDetails(apiKey, imdbID)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : ApiSuccess<Movie>() {
                override fun call(movie: Movie) {
                    if (movie.response == "True") {
                        listPresenterImplInterface!!.successMovieDetails(movie, targetView)
                    } else if (movie.response == "False") {
                        listPresenterImplInterface!!.failMovieDetails("Something Went Wrong!!")
                    }
                }
            }, object : ApiFail() {
                override fun httpStatus(response: HttpErrorResponse) {
                    listPresenterImplInterface!!.failMovieDetails(response.error)
                }

                override fun noNetworkError() {
                    listPresenterImplInterface!!.failMovieDetails("Please Check Your Internet")
                }

                override fun unknownError(throwable: Throwable) {
                    listPresenterImplInterface!!.failMovieDetails(throwable.message)
                }
            })
    }
}

