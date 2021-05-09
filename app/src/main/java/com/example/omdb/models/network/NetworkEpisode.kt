package com.example.omdb.models.network

import com.example.omdb.models.Episode
import com.google.gson.annotations.SerializedName

data class NetworkEpisode(
    @SerializedName("imdbID") val _id: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Episode") val episode: String,
    @SerializedName("Released") val released: String
)

fun NetworkEpisode.parse(): Episode = Episode(
    _id = _id,
    title = title,
    episode = episode,
    released = released
)

fun List<NetworkEpisode?>.parse(): List<Episode> = mapNotNull { it?.parse() }