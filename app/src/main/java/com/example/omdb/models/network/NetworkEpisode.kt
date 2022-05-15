package com.example.omdb.models.network

import com.example.omdb.models.local.EntityEpisode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkEpisode(
    @SerialName("imdbID") val _id: String,
    @SerialName("Title") val title: String? = null,
    @SerialName("Episode") val episode: String? = null,
    @SerialName("Released") val released: String? = null
)

fun NetworkEpisode.parse(
    contentId: String,
    season: Int
): EntityEpisode = EntityEpisode(
    _id = _id,
    contentId = contentId,
    season = season,
    title = title,
    episode = episode,
    released = released
)

fun List<NetworkEpisode?>.parse(
    contentId: String,
    season: Int
): List<EntityEpisode> = mapNotNull { it?.parse(contentId, season) }