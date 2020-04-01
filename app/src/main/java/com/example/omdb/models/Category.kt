package com.example.omdb.models

data class Category(
    val _id: Type,
    val icon: Int,
    val background: Int,
    val title: String
)

enum class Type {
    MOVIES {
        override fun toString(): String {
            return "Movies"
        }
    },
    SERIES {
        override fun toString(): String {
            return "Series"
        }
    },
    EPISODES {
        override fun toString(): String {
            return "Episodes"
        }
    }
}