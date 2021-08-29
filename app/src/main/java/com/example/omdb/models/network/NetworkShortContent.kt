package com.example.omdb.models.network

import com.example.omdb.models.Type
import com.example.omdb.models.local.EntityShortContent
import com.google.gson.annotations.SerializedName

data class NetworkShortContent(
    @SerializedName("imdbID") val _id: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Poster") val poster: String?
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