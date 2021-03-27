package com.example.omdb.ui.details.episodes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.omdb.R
import com.example.omdb.models.Episode
import kotlinx.android.synthetic.main.layout_episode_item.view.*

class EpisodesAdapter(private val season: Int) :
    ListAdapter<Episode, EpisodesAdapter.ViewHolder>(EpisodeDC()) {

    // Global
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_episode_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun swapData(data: List<Episode>) = submitList(data.toMutableList())

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(episode: Episode) = with(itemView) {

            txt_title.text = episode.title

            val bullet = resources.getString(R.string.divider_bullet)
            txt_released.text = "S$season $bullet E${episode.episode} | ${episode.released}"

            setOnClickListener { listener?.episodeClicked(episode) }
        }
    }


    private class EpisodeDC : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(
            oldItem: Episode,
            newItem: Episode
        ): Boolean = oldItem._id == newItem._id

        override fun areContentsTheSame(
            oldItem: Episode,
            newItem: Episode
        ): Boolean = oldItem == newItem
    }

    interface Listener {
        fun episodeClicked(episode: Episode)
    }
}
