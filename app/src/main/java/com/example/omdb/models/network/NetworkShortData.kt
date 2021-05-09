package com.example.omdb.models.network

import com.example.omdb.models.ShortData
import com.google.gson.annotations.SerializedName

data class NetworkShortData(
    @SerializedName("imdbID") val _id: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Poster") val poster: String?
)

fun NetworkShortData.parse(): ShortData = ShortData(
    _id = _id,
    title = title,
    year = year,
    poster = poster
)

fun List<NetworkShortData?>.parse(): List<ShortData> = mapNotNull { it?.parse() }