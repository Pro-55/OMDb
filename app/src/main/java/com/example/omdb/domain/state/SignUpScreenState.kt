package com.example.omdb.domain.state

data class SignUpScreenState(
    val firstName: TextFieldState = TextFieldState(),
    val lastName: TextFieldState = TextFieldState(),
    val email: TextFieldState = TextFieldState(),
    val profileUrl: String? = null
)