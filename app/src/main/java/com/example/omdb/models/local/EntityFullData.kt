package com.example.omdb.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.omdb.models.FullData
import com.example.omdb.models.TeamDetails
import com.example.omdb.models.Type

@Entity(tableName = "full_data_table")
data class EntityFullData(

    @PrimaryKey(autoGenerate = false)
    val _id: String,

    val type: Type?,

    val poster: String,

    val title: String,

    val year: String,

    val rated: String,

    val runtime: String,

    val genre: String,

    val director: String,

    val writer: String,

    val actors: String,

    val production: String?,

    val plot: String,

    val language: String,

    val rating: Float,

    val seasons: Int,

    val isFavorite: Boolean
)


fun EntityFullData.parse(ratings: List<EntityRating>?): FullData = FullData(
    _id = _id,
    type = type,
    poster = poster,
    title = title,
    year = year,
    rated = rated,
    runtime = runtime,
    genre = genre,
    team = TeamDetails(
        cast = actors,
        crew = writer,
        director = director,
        production = production
    ),
    plot = plot,
    language = language,
    ratings = ratings?.parse() ?: listOf(),
    rating = rating,
    seasons = seasons,
    isFavorite = isFavorite
)