package com.example.omdb.ui.details.seasons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.omdb.R
import kotlinx.android.synthetic.main.layout_season_item.view.*

class SeasonsAdapter : ListAdapter<Int, SeasonsAdapter.ViewHolder>(
    IntDC()
) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_season_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun swapData(data: List<Int>) = submitList(data.toMutableList())

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(season: Int) = with(itemView) {

            txt_season.text = "Season $season"

            setOnClickListener { listener?.seasonClicked(season) }
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
