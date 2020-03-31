package com.example.omdb.models

import com.google.gson.annotations.SerializedName

data class ShortData(
    @SerializedName("imdbID") val _id: String,
    @SerializedName("Type") val type: Type,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Poster") val poster: String
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false
        other as ShortData?

        if (_id != other._id) return false
        if (type != other.type) return false
        if (title != other.title) return false
        if (year != other.year) return false
        if (poster != other.poster) return false

        return true
    }

    override fun hashCode(): Int {
        var result = _id.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + year.hashCode()
        result = 31 * result + poster.hashCode()
        return result
    }

}