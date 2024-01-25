package com.example.omdb.data.network.model

import com.example.omdb.data.local.model.EntityContent
import com.example.omdb.domain.model.Type
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkContent(
    @SerialName("imdbID") val _id: String,
    @SerialName("Type") val type: String,
    @SerialName("Poster") val poster: String? = null,
    @SerialName("Title") val title: String,
    @SerialName("Year") val year: String? = null,
    @SerialName("Rated") val rated: String? = null,
    @SerialName("Runtime") val runtime: String? = null,
    @SerialName("Genre") val genre: String? = null,
    @SerialName("Director") val director: String? = null,
    @SerialName("Writer") val writer: String? = null,
    @SerialName("Actors") val actors: String? = null,
    @SerialName("Plot") val plot: String? = null,
    @SerialName("Language") val language: String? = null,
    @SerialName("Ratings") val ratings: List<NetworkRating?>? = null,
    @SerialName("imdbRating") val imdbRating: String? = null,
    @SerialName("Production") val production: String? = null,
    @SerialName("totalSeasons") val seasons: String? = null
)

fun NetworkContent.parse(isFavorite: Boolean): EntityContent = EntityContent(
    _id = _id,
    type = when (type) {
        "movie" -> Type.MOVIE
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
    actors = actors,
    writer = writer,
    director = director,
    production = production,
    plot = plot,
    language = language,
    rating = try {
        imdbRating?.toFloat() ?: 0F
    } catch (e: Exception) {
        0F
    } / 2,
    seasons = try {
        seasons?.toInt() ?: 0
    } catch (e: Exception) {
        0
    },
    isFavorite = isFavorite
)