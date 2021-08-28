package com.example.omdb.models.network

import com.example.omdb.models.SearchResult
import com.example.omdb.models.local.EntityContent
import com.example.omdb.models.local.parse
import com.example.omdb.models.toShortData
import com.google.gson.annotations.SerializedName

data class NetworkSearchResult(
    @SerializedName("Search") val search: List<NetworkContent?>?,
    @SerializedName("totalResults") val totalResults: String?
)

fun NetworkSearchResult.parse(results: List<EntityContent>): SearchResult = SearchResult(
    search = results.parse().toShortData(),
    totalResults = totalResults ?: "0"
)