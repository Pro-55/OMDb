package com.example.omdb.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShortContent(
    val _id: String,
    val title: String,
    val year: String,
    val poster: String?
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false
        other as ShortContent?

        if (_id != other._id) return false
        if (title != other.title) return false
        if (year != other.year) return false
        if (poster != other.poster) return false

        return true
    }

    override fun hashCode(): Int {
        var result = _id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + year.hashCode()
        result = 31 * result + (poster?.hashCode() ?: 0)
        return result
    }
}