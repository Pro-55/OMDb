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
    @SerializedName("Awards") val awards: String,
    @SerializedName("Ratings") val ratings: List<Rating>,
    @SerializedName("imdbRating") val imdbRating: String,
    @SerializedName("Production") val production: String?,
    @SerializedName("totalSeasons") val seasons: String?
)