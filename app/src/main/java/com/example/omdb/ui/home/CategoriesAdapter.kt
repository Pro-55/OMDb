package com.example.omdb.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.omdb.R
import com.example.omdb.models.Category
import kotlinx.android.synthetic.main.layout_category_item.view.*

class CategoriesAdapter : ListAdapter<Category, CategoriesAdapter.ViewHolder>(CategoryDC()) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_category_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun swapData(data: List<Category>) = submitList(data.toMutableList())

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: Category) = with(itemView) {

            view_background.setBackgroundResource(category.background)

            img_icon.setImageDrawable(resources.getDrawable(category.icon))

            txt_title.text = category.title

            setOnClickListener { listener?.onClick(category, this.img_icon) }

        }
    }


    private class CategoryDC : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean = oldItem._id == newItem._id

        override fun areContentsTheSame(
            oldItem: Category,
            newItem: Category
        ): Boolean = oldItem == newItem
    }

    interface Listener {
        fun onClick(category: Category, sharedView: View)
    }
}
