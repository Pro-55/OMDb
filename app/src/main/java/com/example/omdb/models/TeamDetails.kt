package com.example.omdb.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamDetails(
    val cast: String,
    val crew: String,
    val director: String,
    val production: String?
) : Parcelable