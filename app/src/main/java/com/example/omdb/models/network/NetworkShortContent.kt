package com.example.omdb.models.network

import com.example.omdb.models.Type
import com.example.omdb.models.local.EntityShortContent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkShortContent(
    @SerialName("imdbID") val _id: String,
    @SerialName("Title") val title: String,
    @SerialName("Year") val year: String,
    @SerialName("Poster") val poster: String? = null
)

fun NetworkShortContent.parse(type: Type): EntityShortContent = EntityShortContent(
    _id = _id,
    type = type,
    poster = poster,
    title = title,
    year = year
)

fun List<NetworkShortContent?>.parse(type: Type): List<EntityShortContent> =
    mapNotNull { it?.parse(type) }