package com.example.omdb.models.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.omdb.models.Episode
import com.example.omdb.util.Constants

@Entity(tableName = "episode_table")
data class EntityEpisode(

    @PrimaryKey(autoGenerate = false)
    val _id: String,

    @ColumnInfo(name = "content_id")
    val contentId: String,

    val season: Int,

    val title: String?,

    val episode: String?,

    val released: String?

)

fun EntityEpisode.parse(): Episode = Episode(
    _id = _id,
    title = title ?: Constants.NOT_AVAILABLE,
    episode = episode ?: Constants.NOT_AVAILABLE,
    released = released ?: Constants.NOT_AVAILABLE
)

fun List<EntityEpisode?>.parse(): List<Episode> = mapNotNull { it?.parse() }