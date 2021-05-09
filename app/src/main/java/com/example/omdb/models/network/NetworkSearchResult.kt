package com.example.omdb.models.network

import com.example.omdb.models.SearchResult
import com.google.gson.annotations.SerializedName

data class NetworkSearchResult(
    @SerializedName("Search") val search: List<NetworkShortData>,
    @SerializedName("totalResults") val totalResults: String
)

fun NetworkSearchResult.parse(): SearchResult = SearchResult(
    search = search.parse(),
    totalResults = totalResults
)