package com.example.omdb.domain.model

enum class Type {
    MOVIE,
    SERIES,
    EPISODES;

    override fun toString(): String {
        return super.toString().lowercase()
    }
}