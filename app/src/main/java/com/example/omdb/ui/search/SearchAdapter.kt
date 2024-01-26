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
import com.example.omdb.domain.model.ShortContent
import com.example.omdb.util.extensions.addPosterPlaceholder
import com.example.omdb.util.extensions.diskCacheStrategyAll
import com.example.omdb.util.listners.OnHoldListener

class SearchAdapter(
    private val glide: RequestManager
) : ListAdapter<ShortContent, SearchAdapter.ViewHolder>(ShortDataDC()) {

    // Global
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.layout_search_item, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun swapData(data: List<ShortContent>) = submitList(data.toMutableList())

    inner class ViewHolder(
        private val binding: LayoutSearchItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(content: ShortContent) = with(binding) {

            glide.load(content.poster)
                .diskCacheStrategyAll()
                .addPosterPlaceholder(root.context)
                .into(imgPoster)

            root.transitionName = content._id
            imgPoster.transitionName = content.poster

            root.setOnTouchListener(OnHoldListener(object : OnHoldListener.Listener {
                override fun onClick() {
                    root.performClick()
                }

                override fun onHold() {
                    listener?.onHold(content)
                }

                override fun onRelease() {
                    listener?.onRelease()
                }
            }))

            root.setOnClickListener { listener?.onClick(content, root, imgPoster) }

        }
    }


    private class ShortDataDC : DiffUtil.ItemCallback<ShortContent>() {
        override fun areItemsTheSame(
            oldItem: ShortContent,
            newItem: ShortContent
        ): Boolean = oldItem._id == newItem._id

        override fun areContentsTheSame(
            oldItem: ShortContent,
            newItem: ShortContent
        ): Boolean = oldItem == newItem
    }

    interface Listener {
        fun onClick(content: ShortContent, sharedCard: View, sharedImage: View)
        fun onHold(content: ShortContent)
        fun onRelease()
    }

}