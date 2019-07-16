package com.example.omdb.listmodule

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat.makeSceneTransitionAnimation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.omdb.R
import com.example.omdb.detailsmodule.DetailsActivity
import com.example.omdb.network.responce.Movie
import com.example.omdb.network.responce.SearchData
import com.jakewharton.rxbinding.widget.RxTextView
import kotlinx.android.synthetic.main.activity_main.*
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class ListActivity : AppCompatActivity(), IMovieList, IListPresenter {

    //var
    private var listPresenter: ListPresenter? = null
    private var listAdapter: SearchListAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private var searchText = ""
    private var totalCount: String? = "1"

    //val
    private val TAG = "ListActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listPresenter = ListPresenter(this)

        //RecyclerView Setup
        listAdapter = SearchListAdapter(applicationContext, this)
        layoutManager = LinearLayoutManager(applicationContext)
        idRvMovieSearchList.adapter = listAdapter
        idRvMovieSearchList.layoutManager = layoutManager

        idRvMovieSearchList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val adapterSize = listAdapter!!.itemCount
                val lastItemIndex = layoutManager!!.findLastCompletelyVisibleItemPosition()
                if (lastItemIndex > 0 && adapterSize == lastItemIndex + 1 && totalCount!!.toInt() > adapterSize) {
                    listPresenter!!.getMoreResults(
                        searchText,
                        showLoader(getString(R.string.string_loading_more))
                    )
                }
            }
        })

        RxTextView.textChanges(idEtMovieSearchBox)
            .skip(1)
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<CharSequence>() {
                override fun onCompleted() {
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "TestLog: ${e.message}")
                }

                override fun onNext(searchBoxText: CharSequence) {
                    searchText = searchBoxText.toString().trim()
                    if (searchText.isNotBlank() && searchText.isNotEmpty()) {
                        listPresenter!!.getSearchResult(
                            searchText,
                            showLoader(getString(R.string.string_searching_movies))
                        )
                    }
                    listAdapter!!.clearSearchList()
                }
            })
    }

    override fun successSearch(
        searchData: List<SearchData>?,
        totalResults: String?,
        loader: ProgressDialog
    ) {
        totalCount = totalResults
        hideLoader(loader)
        if (searchData != null) {
            idTvError.visibility = View.GONE
            listAdapter!!.updateSearchList(ArrayList(searchData))
        }
    }

    override fun failSearch(error: String?, loader: ProgressDialog) {
        hideLoader(loader)
        listAdapter!!.clearSearchList()
        if (searchText.trim().isNotBlank()) {
            idTvError.text = error
        } else {
            idTvError.text = resources.getString(R.string.string_search_something)
        }
        idTvError.visibility = View.VISIBLE
    }

    override fun showMovieDetails(searchData: SearchData, targetView: AppCompatImageView) {
        listPresenter!!.getMovieDetails(
            searchData.imdbID,
            targetView,
            showLoader(getString(R.string.string_fetching_movie))
        )
    }

    override fun successGetDetails(
        movie: Movie,
        targetView: AppCompatImageView,
        loader: ProgressDialog
    ) {
        hideLoader(loader)
        val activityTransitionBundle =
            makeSceneTransitionAnimation(this, targetView, targetView.transitionName).toBundle()
        val movieDetailsIntent = Intent(this, DetailsActivity::class.java)
        movieDetailsIntent.putExtra("movie", movie)
        startActivity(movieDetailsIntent, activityTransitionBundle)
    }

    override fun failGetDetails(error: String?, loader: ProgressDialog) {
        hideLoader(loader)
        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
    }

    private fun showLoader(message: String): ProgressDialog {
        return ProgressDialog.show(this, "", message, true, false)
    }

    private fun hideLoader(loader: ProgressDialog) {
        loader.dismiss()
    }
}
