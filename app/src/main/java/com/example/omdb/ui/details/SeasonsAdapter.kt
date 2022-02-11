package com.example.omdb.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.omdb.R
import com.example.omdb.databinding.LayoutSeasonItemBinding

class SeasonsAdapter : ListAdapter<Int, SeasonsAdapter.ViewHolder>(IntDC()) {

    // Global
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.layout_season_item, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun swapData(data: List<Int>) = submitList(data.toMutableList())

    inner class ViewHolder(
        private val binding: LayoutSeasonItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(season: Int) = with(binding) {
            chipSeason.apply {
                text = "Season $season"
                setOnClickListener { listener?.seasonClicked(season) }
            }
            Unit
        }
    }


    private class IntDC : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(
            oldItem: Int,
            newItem: Int
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Int,
            newItem: Int
        ): Boolean = oldItem == newItem
    }

    interface Listener {
        fun seasonClicked(season: Int)
    }
}