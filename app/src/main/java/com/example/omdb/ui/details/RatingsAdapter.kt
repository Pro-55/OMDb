package com.example.omdb.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.omdb.R
import com.example.omdb.models.Rating
import kotlinx.android.synthetic.main.layout_rating_item.view.*

class RatingsAdapter : ListAdapter<Rating, RatingsAdapter.ViewHolder>(RatingDC()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_rating_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun swapData(data: List<Rating>) = submitList(data.toMutableList())

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(rating: Rating) = with(itemView) {

            txt_rating_value.text = rating.value

            txt_rating_source.text = rating.source
        }
    }


    private class RatingDC : DiffUtil.ItemCallback<Rating>() {
        override fun areItemsTheSame(
            oldItem: Rating,
            newItem: Rating
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Rating,
            newItem: Rating
        ): Boolean = oldItem == newItem
    }
}
