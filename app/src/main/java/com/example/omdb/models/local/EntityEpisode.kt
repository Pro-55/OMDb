package com.example.omdb.models.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.omdb.models.Episode

@Entity(tableName = "episode_table")
data class EntityEpisode(

    @PrimaryKey(autoGenerate = false)
    val _id: String,

    @ColumnInfo(name = "content_id")
    val contentId: String,

    val season: Int,

    val title: String,

    val episode: String,

    val released: String

)

fun EntityEpisode.parse(): Episode = Episode(
    _id = _id,
    title = title,
    episode = episode,
    released = released
)

fun List<EntityEpisode?>.parse(): List<Episode> = mapNotNull { it?.parse() }