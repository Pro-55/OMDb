package com.example.omdb.models.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSeason(
    @SerialName("Episodes") val episodes: List<NetworkEpisode?>? = null
)