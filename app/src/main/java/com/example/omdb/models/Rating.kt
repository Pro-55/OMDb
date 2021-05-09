package com.example.omdb.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rating(
    @SerializedName("Source") val source: String,
    @SerializedName("Value") val value: String
) : Parcelable