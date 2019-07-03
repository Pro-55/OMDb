package com.example.omdb.detailsmodule

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.omdb.R
import com.example.omdb.network.responce.Movie
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    //var
    private var listAdapter: RatingsAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private var layoutParams: FrameLayout.LayoutParams? = null

    //val
    private val TAG = "DetailsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val movie: Movie = intent.getParcelableExtra("movie")

        val url = movie.poster
        if (url != null) {
            Glide.with(applicationContext)
                .load(url)
                .placeholder(R.drawable.ic_default_image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        setLayoutParams(resource.intrinsicWidth, resource.intrinsicHeight)
                        return false
                    }
                })
                .into(idIvDetailsPoster)
        }

        idTvDetailsTitle.text = movie.title

        if (!movie.rated!!.equals("N/A", true)) {
            idTvDetailsRated.text = movie.rated
            idTvDetailsRated.visibility = View.VISIBLE
        }

        if (!movie.released!!.equals("N/A", true)) {
            idTvDetailsReleased.text = movie.released
            idTvDetailsReleased.visibility = View.VISIBLE
        }

        if (!movie.imdbRating!!.equals("N/A", true)) {
            idRbMovieRatings.rating = movie.imdbRating!!.toFloat() / 2
            idRbMovieRatings.visibility = View.VISIBLE
        }

        if (!movie.runtime!!.equals("N/A", true)) {
            idTvDetailsRuntime.text = movie.runtime
            idTvDetailsRuntime.visibility = View.VISIBLE
        }

        if (!movie.genre!!.equals("N/A", true)) {
            idTvDetailsGenre.text = movie.genre
            idTvDetailsGenre.visibility = View.VISIBLE
        }

        if (!movie.language!!.equals("N/A", true)) {
            idTvDetailsLanguages.text = movie.language
            idTvDetailsLanguages.visibility = View.VISIBLE
        }

        if (!movie.country!!.equals("N/A", true)) {
            idTvDetailsCountries.text = movie.country
            idTvDetailsCountries.visibility = View.VISIBLE
        }

        if (!movie.actors!!.equals("N/A", true)) {
            idTvDetailsActors.text = movie.actors
            idTvDetailsActors.visibility = View.VISIBLE
        }

        if (!movie.plot!!.equals("N/A", true)) {
            idTvDetailsPlot.text = movie.plot
            idTvDetailsPlot.visibility = View.VISIBLE
        }

        if (!movie.awards!!.equals("N/A", true)) {
            idTvDetailsAwards.text = movie.awards
            idTvDetailsAwards.visibility = View.VISIBLE
        }

        if (!movie.production!!.equals("N/A", true)) {
            idTvDetailsProduction.text = movie.production
            idTvDetailsProduction.visibility = View.VISIBLE
        }

        //RecyclerView Setup
        listAdapter = RatingsAdapter(applicationContext, movie.ratings!!)
        layoutManager = LinearLayoutManager(applicationContext)
        idRvMoreRatings.adapter = listAdapter
        idRvMoreRatings.layoutManager = layoutManager
        if (listAdapter!!.itemCount > 0) {
            idRvMoreRatings.visibility = View.VISIBLE
        }
    }

    fun setLayoutParams(width: Int, height: Int) {
        layoutParams = FrameLayout.LayoutParams(width, height)
        idIvDetailsPoster.layoutParams = layoutParams
    }
}
