package com.example.omdb.domain.model

data class Content(
    val _id: String,
    val type: Type,
    val poster: String?,
    val title: String,
    val year: String,
    val rated: String,
    val runtime: String,
    val genre: String,
    val team: TeamDetails,
    val plot: String,
    val language: String,
    val ratings: List<Rating>,
    val rating: Float,
    val seasons: Int,
    val isFavorite: Boolean
) {

    fun isNotSeries(): Boolean = type != Type.SERIES

}