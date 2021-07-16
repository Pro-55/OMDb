package com.example.omdb.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.omdb.R
import com.example.omdb.databinding.LayoutSearchItemBinding
import com.example.omdb.models.ShortData
import com.example.omdb.util.extensions.addPosterPlaceholder
import com.example.omdb.util.extensions.diskCacheStrategyAll
import com.example.omdb.util.listners.OnHoldListener

class SearchAdapter(
    private val glide: RequestManager
) : ListAdapter<ShortData, SearchAdapter.ViewHolder>(ShortDataDC()) {

    // Global
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.layout_search_item, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun swapData(data: List<ShortData>) = submitList(data.toMutableList())

    inner class ViewHolder(
        private val binding: LayoutSearchItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ShortData) = with(binding) {

            glide.load(data.poster)
                .diskCacheStrategyAll()
                .addPosterPlaceholder(root.context)
                .into(imgPoster)

            root.transitionName = data._id
            imgPoster.transitionName = data.poster

            root.setOnTouchListener(OnHoldListener(object : OnHoldListener.Listener {
                override fun onClick() {
                    root.performClick()
                }

                override fun onHold() {
                    listener?.onHold(data)
                }

                override fun onRelease() {
                    listener?.onRelease()
                }
            }))

            root.setOnClickListener { listener?.onClick(data, root, imgPoster) }

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
        fun onClick(data: ShortData, sharedCard: View, sharedImage: View)
        fun onHold(data: ShortData)
        fun onRelease()
    }

}
