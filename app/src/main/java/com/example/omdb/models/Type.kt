package com.example.omdb.models

enum class Type {
    MOVIE,
    SERIES,
    EPISODES;

    override fun toString(): String {
        return super.toString().lowercase()
    }
}