package com.example.omdb.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.omdb.models.ShortContent
import com.example.omdb.models.Type
import com.example.omdb.util.Constants

@Entity(tableName = "short_content_table")
data class EntityShortContent(
    @PrimaryKey(autoGenerate = false)
    val _id: String,

    val type: Type,

    val poster: String?,

    val title: String,

    val year: String?,
)

fun EntityShortContent.parse(): ShortContent = ShortContent(
    _id = _id,
    title = title,
    year = year ?: Constants.NOT_AVAILABLE,
    poster = poster
)

fun List<EntityShortContent?>.parse(): List<ShortContent> = mapNotNull { it?.parse() }