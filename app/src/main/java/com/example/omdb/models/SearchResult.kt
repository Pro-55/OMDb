package com.example.omdb.models

import com.google.gson.annotations.SerializedName

data class SearchResult (
    @SerializedName("Search") val search: List<ShortData>,
    @SerializedName("totalResults") val totalResults: String,
    @SerializedName("Response") val response: String,
    @SerializedName("Error") val error: String
)