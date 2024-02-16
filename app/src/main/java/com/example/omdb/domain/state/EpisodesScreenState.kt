package com.example.omdb.domain.state

import com.example.omdb.domain.model.Episode

data class EpisodesScreenState(
    val _id: String = "",
    val season: Int = 0,
    val episodes: List<Episode> = emptyList()
)