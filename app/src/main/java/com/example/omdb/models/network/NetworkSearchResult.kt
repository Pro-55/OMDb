package com.example.omdb.models.network

import com.example.omdb.models.SearchResult
import com.example.omdb.models.local.EntityShortContent
import com.example.omdb.models.local.parse
import com.google.gson.annotations.SerializedName

data class NetworkSearchResult(
    @SerializedName("Search") val search: List<NetworkShortContent?>?,
    @SerializedName("totalResults") val totalResults: String?
)

fun NetworkSearchResult.parse(results: List<EntityShortContent>): SearchResult = SearchResult(
    search = results.parse(),
    totalResults = totalResults ?: "0"
)