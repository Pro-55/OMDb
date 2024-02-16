package com.example.omdb.domain.state

data class TextFieldState(
    val text: String = "",
    val error: String? = null
)