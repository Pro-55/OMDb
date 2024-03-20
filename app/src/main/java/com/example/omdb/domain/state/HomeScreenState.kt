package com.example.omdb.domain.state

import com.example.omdb.domain.model.User

data class HomeScreenState(
    val greeting: String = "",
    val user: User? = null
)