package com.example.omdb.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.omdb.R
import com.example.omdb.models.ShortData
import kotlinx.android.synthetic.main.layout_search_item.view.*

class SearchAdapter : ListAdapter<ShortData, SearchAdapter.ViewHolder>(ShortDataDC()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_search_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun swapData(data: List<ShortData>) = submitList(data.toMutableList())

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: ShortData) = with(itemView) {
            txt_name.text = data.title
        }
    }


    private class ShortDataDC : DiffUtil.ItemCallback<ShortData>() {
        override fun areItemsTheSame(
            oldItem: ShortData,
            newItem: ShortData
        ): Boolean = oldItem._id == newItem._id

        override fun areContentsTheSame(
            oldItem: ShortData,
            newItem: ShortData
        ): Boolean = oldItem == newItem
    }
}
