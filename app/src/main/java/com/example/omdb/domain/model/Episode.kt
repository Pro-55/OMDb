package com.example.omdb.domain.model

data class Episode(
    val _id: String,
    val title: String,
    val episode: String,
    val released: String
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false
        other as Episode?

        if (_id != other._id) return false
        if (episode != other.episode) return false
        if (title != other.title) return false
        return released == other.released
    }

    override fun hashCode(): Int {
        var result = _id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + episode.hashCode()
        result = 31 * result + released.hashCode()
        return result
    }
}