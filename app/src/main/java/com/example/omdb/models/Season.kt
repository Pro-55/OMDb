package com.example.omdb.models

import com.google.gson.annotations.SerializedName

data class Season (
    @SerializedName("Episodes") val episodes: List<Episode>
)