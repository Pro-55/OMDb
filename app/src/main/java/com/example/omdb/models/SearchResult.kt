package com.example.omdb.models

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("Search") val search: List<ShortData> = listOf(),
    @SerializedName("totalResults") val totalResults: String = "0"
)