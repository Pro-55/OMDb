package com.example.omdb.models.network

import com.example.omdb.models.SearchResult
import com.example.omdb.models.local.EntityShortContent
import com.example.omdb.models.local.parse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSearchResult(
    @SerialName("Search") val search: List<NetworkShortContent?>? = null,
    @SerialName("totalResults") val totalResults: String? = null
)

fun NetworkSearchResult.parse(results: List<EntityShortContent>): SearchResult = SearchResult(
    search = results.parse(),
    totalResults = totalResults ?: "0"
)