package com.example.omdb.models

import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("imdbID") val _id: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Episode") val episode: String,
    @SerializedName("Released") val released: String
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false
        other as Episode?

        if (_id != other._id) return false
        if (episode != other.episode) return false
        if (title != other.title) return false
        if (released != other.released) return false

        return true
    }

    override fun hashCode(): Int {
        var result = _id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + episode.hashCode()
        result = 31 * result + released.hashCode()
        return result
    }
}