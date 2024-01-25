package com.example.omdb.domain.model

data class User(
    val _id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val profileUrl: String?
)