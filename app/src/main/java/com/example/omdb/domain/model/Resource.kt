package com.example.omdb.domain.model

sealed interface Resource<T> {
    class Loading<T> : Resource<T>
    class Success<T>(val data: T?) : Resource<T>
    class Error<T>(val msg: String?) : Resource<T>
}