package com.example.omdb.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSeason(
    @SerialName("Episodes") val episodes: List<NetworkEpisode?>? = null
)