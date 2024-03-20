package com.example.omdb.util.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> String.toObject(): T? = try {
    Gson().fromJson(this, T::class.java)
} catch (e: Exception) {
    null
}

inline fun <reified T> String.toObjectList(): List<T>? = try {
    Gson().fromJson<List<T>>(this, object : TypeToken<List<T>>() {}.type)
} catch (e: Exception) {
    null
}