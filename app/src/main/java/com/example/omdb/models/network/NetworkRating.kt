package com.example.omdb.models.network

import com.example.omdb.models.local.EntityRating
import com.example.omdb.util.Constants
import com.google.gson.annotations.SerializedName

data class NetworkRating(
    @SerializedName("Source") val source: String?,
    @SerializedName("Value") val value: String?
)

fun NetworkRating.parse(contentId: String): EntityRating = EntityRating(
    contentId = contentId,
    source = source ?: Constants.NOT_AVAILABLE,
    value = value ?: Constants.NOT_AVAILABLE
)

fun List<NetworkRating?>.parse(contentId: String): List<EntityRating> =
    mapNotNull { it?.parse(contentId) }