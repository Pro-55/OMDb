package com.example.omdb.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.omdb.R
import com.example.omdb.models.ShortData
import kotlinx.android.synthetic.main.layout_search_item.view.*

class SearchAdapter(private val glide: RequestManager) :
    ListAdapter<ShortData, SearchAdapter.ViewHolder>(ShortDataDC()) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_search_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun swapData(data: List<ShortData>) = submitList(data.toMutableList())

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: ShortData) = with(itemView) {
            glide.load(data.poster)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(resources.getDrawable(R.drawable.placeholder_poster))
                .into(img_poster)

            transitionName = data._id
            setOnClickListener { listener?.onClick(adapterPosition, data, this) }

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

    interface Listener {
        fun onClick(position: Int, data: ShortData, sharedView: View)
    }

}
