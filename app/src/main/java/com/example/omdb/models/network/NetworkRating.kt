package com.example.omdb.models.network

import com.example.omdb.models.local.EntityRating
import com.example.omdb.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkRating(
    @SerialName("Source") val source: String? = null,
    @SerialName("Value") val value: String? = null
)

fun NetworkRating.parse(contentId: String): EntityRating = EntityRating(
    contentId = contentId,
    source = source ?: Constants.NOT_AVAILABLE,
    value = value ?: Constants.NOT_AVAILABLE
)

fun List<NetworkRating?>.parse(contentId: String): List<EntityRating> =
    mapNotNull { it?.parse(contentId) }