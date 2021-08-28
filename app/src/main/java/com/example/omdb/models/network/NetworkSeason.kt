package com.example.omdb.models.network

import com.google.gson.annotations.SerializedName

data class NetworkSeason(
    @SerializedName("Episodes") val episodes: List<NetworkEpisode?>?
)