package com.example.omdb.data.network.model

import com.example.omdb.data.local.model.EntityShortContent
import com.example.omdb.domain.model.Type
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