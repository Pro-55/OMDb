package com.example.omdb.domain.state

import com.example.omdb.domain.model.TeamDetails

data class TeamDetailsScreenState(
    val team: TeamDetails = TeamDetails()
)