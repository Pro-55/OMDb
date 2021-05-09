package com.example.omdb.models.network

import com.example.omdb.models.Rating
import com.google.gson.annotations.SerializedName

data class NetworkRating(
    @SerializedName("Source") val source: String,
    @SerializedName("Value") val value: String
)

fun NetworkRating.parse(): Rating = Rating(
    source = source,
    value = value
)

fun List<NetworkRating?>.parse(): List<Rating> = mapNotNull { it?.parse() }