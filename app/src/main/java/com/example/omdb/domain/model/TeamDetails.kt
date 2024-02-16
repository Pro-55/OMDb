package com.example.omdb.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamDetails(
    val cast: String = "",
    val crew: String = "",
    val director: String = "",
    val production: String? = null
) : Parcelable