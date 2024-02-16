package com.example.omdb.domain.state

import com.example.omdb.domain.model.Rating

data class RatingsScreenState(
    val ratings: List<Rating> = emptyList()
)