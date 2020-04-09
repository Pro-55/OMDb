package com.example.omdb.models

import com.google.gson.annotations.SerializedName

data class FullData(
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
    @SerializedName("Ratings") val ratings: List<Rating>,
    @SerializedName("imdbRating") val imdbRating: String? = null,
    @SerializedName("Production") val production: String? = null,
    @SerializedName("totalSeasons") val seasons: String? = null
) {
    val contentType: Type?
        get() = when (type) {
            "movie" -> Type.MOVIES
            "series" -> Type.SERIES
            "episode" -> Type.EPISODES
            else -> null
        }

    val rating: Float
        get() = try {
            imdbRating?.toFloat() ?: 0F
        } catch (e: Exception) {
            0F
        }
}