package com.example.omdb.data.network.model

import com.example.omdb.data.local.model.EntityShortContent
import com.example.omdb.data.local.model.parse
import com.example.omdb.domain.model.SearchResult
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