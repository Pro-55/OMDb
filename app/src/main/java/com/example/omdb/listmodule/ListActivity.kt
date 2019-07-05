package com.example.omdb.listmodule

import android.app.Dialog
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
    private var loader: Dialog? = null
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
                    showLoader(getString(R.string.string_loading_more))
                    listPresenter!!.getMoreResults(searchText)
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
                        showLoader(getString(R.string.string_searching_movies))
                        listPresenter!!.getSearchResult(searchText)
                    }
                    listAdapter!!.clearSearchList()
                }
            })
    }

    override fun successSearch(searchData: List<SearchData>?, totalResults: String?) {
        totalCount = totalResults
        hideLoader()
        if (searchData != null) {
            idTvError.visibility = View.GONE
            listAdapter!!.updateSearchList(ArrayList(searchData))
        }
    }

    override fun failSearch(error: String?) {
        hideLoader()
        listAdapter!!.clearSearchList()
        if (searchText.trim().isNotBlank()) {
            idTvError.text = error
        } else {
            idTvError.text = resources.getString(R.string.string_search_something)
        }
        idTvError.visibility = View.VISIBLE
    }

    override fun showMovieDetails(searchData: SearchData, targetView: AppCompatImageView) {
        showLoader(getString(R.string.string_fetching_movie))
        listPresenter!!.getMovieDetails(searchData.imdbID, targetView)
    }

    override fun successGetDetails(movie: Movie, targetView: AppCompatImageView) {
        hideLoader()
        val activityTransitionBundle =
            makeSceneTransitionAnimation(this, targetView, targetView.transitionName).toBundle()
        val movieDetailsIntent = Intent(this, DetailsActivity::class.java)
        movieDetailsIntent.putExtra("movie", movie)
        startActivity(movieDetailsIntent, activityTransitionBundle)
    }

    override fun failGetDetails(error: String?) {
        hideLoader()
        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
    }

    private fun showLoader(message: String) {
        loader = ProgressDialog.show(this, "", message, true, false)
    }

    private fun hideLoader() {
        if (loader != null) {
            loader!!.dismiss()
        }
    }
}
