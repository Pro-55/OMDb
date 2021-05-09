package com.example.omdb.models.network

import com.example.omdb.models.Season
import com.google.gson.annotations.SerializedName

data class NetworkSeason(
    @SerializedName("Episodes") val episodes: List<NetworkEpisode>
)

fun NetworkSeason.parse(): Season = Season(
    episodes = episodes.parse()
)