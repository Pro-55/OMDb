package com.example.omdb.models.network

import com.example.omdb.models.local.EntityEpisode
import com.google.gson.annotations.SerializedName

data class NetworkEpisode(
    @SerializedName("imdbID") val _id: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Episode") val episode: String,
    @SerializedName("Released") val released: String
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