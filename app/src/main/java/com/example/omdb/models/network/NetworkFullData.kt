package com.example.omdb.models.network

import com.example.omdb.models.FullData
import com.example.omdb.models.TeamDetails
import com.example.omdb.models.Type
import com.google.gson.annotations.SerializedName

data class NetworkFullData(
    @SerializedName("imdbID") val _id: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Poster") val poster: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Rated") val rated: String,
    @SerializedName("Runtime") val runtime: String,
    @SerializedName("Genre") val genre: String,
    @SerializedName("Director") val director: String,
    @SerializedName("Writer") val writer: String,
    @SerializedName("Actors") val actors: String,
    @SerializedName("Plot") val plot: String,
    @SerializedName("Language") val language: String,
    @SerializedName("Ratings") val ratings: List<NetworkRating>,
    @SerializedName("imdbRating") val imdbRating: String? = null,
    @SerializedName("Production") val production: String? = null,
    @SerializedName("totalSeasons") val seasons: String? = null
)

fun NetworkFullData.parse(): FullData = FullData(
    _id = _id,
    type = when (type) {
        "movie" -> Type.MOVIES
        "series" -> Type.SERIES
        "episode" -> Type.EPISODES
        else -> null
    },
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
    ratings = ratings.parse(),
    rating = try {
        imdbRating?.toFloat() ?: 0F
    } catch (e: Exception) {
        0F
    } / 2,
    seasons = try {
        seasons?.toInt() ?: 0
    } catch (e: Exception) {
        0
    }
)