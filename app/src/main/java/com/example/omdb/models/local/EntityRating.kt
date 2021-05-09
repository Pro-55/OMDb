package com.example.omdb.models.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.omdb.models.Rating

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