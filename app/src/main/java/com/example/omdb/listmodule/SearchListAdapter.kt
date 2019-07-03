package com.example.omdb.listmodule

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.omdb.R
import com.example.omdb.network.responce.SearchData
import kotlinx.android.synthetic.main.layout_movie_list_item.view.*
import java.util.*

class SearchListAdapter(
    private val mContext: Context,
    private val mIMovieList: IMovieList
) :
    RecyclerView.Adapter<SearchListAdapter.ItemViewHolder>() {
    private var searchList: ArrayList<SearchData>? = null
    private var layoutParams: RelativeLayout.LayoutParams? = null

    init {
        searchList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.layout_movie_list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val searchData = searchList!![position]


        val url = searchData.poster
        if (url != null) {
            Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.ic_default_image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        setLayoutParams(holder, resource.intrinsicWidth, resource.intrinsicHeight)
                        return false
                    }
                })
                .into(holder.itemView.idIvMoviePoster)
        }
        holder.itemView.idTvMovieTitle.text = searchData.title
        holder.itemView.idTvMovieYear.text = searchData.year
        holder.itemView.setOnClickListener {
            mIMovieList.showMovieDetails(
                searchData,
                holder.itemView.idIvMoviePoster
            )
        }
    }

    override fun getItemCount(): Int {
        return searchList!!.size
    }

    fun updateSearchList(searchList: ArrayList<SearchData>) {
        this.searchList!!.addAll(searchList)
        notifyItemRangeChanged(itemCount, searchList.size)
    }

    fun clearSearchList() {
        this.searchList!!.clear()
        notifyDataSetChanged()
    }

    fun setLayoutParams(holder: ItemViewHolder, width: Int, height: Int) {
        layoutParams = RelativeLayout.LayoutParams(width, height)
        holder.itemView.idIvMoviePoster.layoutParams = layoutParams
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
