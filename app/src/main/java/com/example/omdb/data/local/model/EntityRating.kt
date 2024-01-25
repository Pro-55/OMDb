package com.example.omdb.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.omdb.domain.model.Rating

@Entity(
    tableName = "rating_table",
    primaryKeys = ["content_id", "source"]
)
data class EntityRating(

    @ColumnInfo(name = "content_id")
    val contentId: String,

    val source: String,

    val value: String

)

fun EntityRating.parse(): Rating = Rating(
    source = source,
    value = value
)

fun List<EntityRating?>.parse(): List<Rating> = mapNotNull { it?.parse() }