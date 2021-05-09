package com.example.omdb.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rating(
    val source: String,
    val value: String
) : Parcelable