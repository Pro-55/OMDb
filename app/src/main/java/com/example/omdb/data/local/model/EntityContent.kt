package com.example.omdb.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.omdb.domain.model.Content
import com.example.omdb.domain.model.TeamDetails
import com.example.omdb.domain.model.Type
import com.example.omdb.util.Constants

@Entity(tableName = "content_table")
data class EntityContent(

    @PrimaryKey(autoGenerate = false)
    val _id: String,

    val type: Type?,

    val poster: String?,

    val title: String,

    val year: String?,

    val rated: String?,

    val runtime: String?,

    val genre: String?,

    val director: String?,

    val writer: String?,

    val actors: String?,

    val production: String?,

    val plot: String?,

    val language: String?,

    val rating: Float,

    val seasons: Int,

    val isFavorite: Boolean
)

fun EntityContent.parse(ratings: List<EntityRating>?): Content = Content(
    _id = _id,
    type = type ?: Type.MOVIE,
    poster = poster,
    title = title,
    year = year ?: Constants.NOT_AVAILABLE,
    rated = rated ?: Constants.NOT_AVAILABLE,
    runtime = runtime ?: Constants.NOT_AVAILABLE,
    genre = genre ?: Constants.NOT_AVAILABLE,
    team = TeamDetails(
        cast = actors ?: Constants.NOT_AVAILABLE,
        crew = writer ?: Constants.NOT_AVAILABLE,
        director = director ?: Constants.NOT_AVAILABLE,
        production = production
    ),
    plot = plot ?: Constants.NOT_AVAILABLE,
    language = language ?: Constants.NOT_AVAILABLE,
    ratings = ratings?.parse() ?: listOf(),
    rating = rating,
    seasons = seasons,
    isFavorite = isFavorite
)

fun List<EntityContent?>.parse(): List<Content> = mapNotNull { it?.parse(null) }