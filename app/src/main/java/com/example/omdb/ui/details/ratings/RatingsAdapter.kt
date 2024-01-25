package com.example.omdb.ui.details.ratings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.omdb.R
import com.example.omdb.databinding.LayoutRatingItemBinding
import com.example.omdb.domain.model.Rating

class RatingsAdapter : ListAdapter<Rating, RatingsAdapter.ViewHolder>(RatingDC()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.layout_rating_item, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun swapData(data: List<Rating>) = submitList(data.toMutableList())

    inner class ViewHolder(
        private val binding: LayoutRatingItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(rating: Rating) = with(binding) {

            txtRatingValue.text = rating.value

            txtRatingSource.text = rating.source
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