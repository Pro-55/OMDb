package com.example.omdb.detailsmodule

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.omdb.R
import com.example.omdb.network.responce.Rating
import kotlinx.android.synthetic.main.layout_ratings_list_item.view.*
import kotlin.collections.ArrayList

class RatingsAdapter(
    private val mContext: Context,
    ratings: List<Rating>
) :
    RecyclerView.Adapter<RatingsAdapter.ItemViewHolder>() {

    private var ratingsList: ArrayList<Rating>? = null

    init {
        ratingsList = ratings as ArrayList<Rating>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.layout_ratings_list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ratingsList!!.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.idTvRatingsSource.text = ratingsList!![position].source
        holder.itemView.idTvRatingsValue.text = ratingsList!![position].value
    }

    fun updateRatingsList(ratings: List<Rating>) {
        ratingsList!!.addAll(ratings)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
