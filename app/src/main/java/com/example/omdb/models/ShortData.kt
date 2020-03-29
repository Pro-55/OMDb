package com.example.omdb.models

import com.google.gson.annotations.SerializedName

data class ShortData(
    @SerializedName("imdbID") val _id: String,
    @SerializedName("Type") val type: Type,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Poster") val poster: String
)
