package com.example.omdb.ui.search

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.omdb.R
import com.example.omdb.models.ShortData
import com.example.omdb.util.extensions.animateScale
import kotlinx.android.synthetic.main.layout_search_item.view.*
import java.util.*

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

            var downTime: Long = -1
            var isPeeking = false

            glide.load(data.poster)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(resources.getDrawable(R.drawable.placeholder_poster))
                .into(img_poster)

            transitionName = data._id
            img_poster.transitionName = data.poster

            setOnTouchListener { v, mE ->
                when (mE.action) {
                    MotionEvent.ACTION_DOWN -> {
                        downTime = Date().time
                        v.animateScale(sX = 0.95F, sY = 0.95F)
                    }
                    MotionEvent.ACTION_MOVE -> if (Date().time - downTime > 500) {
                        if (!isPeeking) {
                            isPeeking = true
                            listener?.onHold(data)
                        }
                    }
                    MotionEvent.ACTION_CANCEL -> v.animateScale()
                    MotionEvent.ACTION_UP -> {
                        v.animateScale()
                        if (isPeeking) {
                            isPeeking = false
                            listener?.onRelease()
                        } else performClick()
                    }
                }
                true
            }

            setOnClickListener { listener?.onClick(data, this, this.img_poster) }

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
