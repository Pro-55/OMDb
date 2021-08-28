package com.example.omdb.models

data class SearchResult(
    val search: List<ShortContent> = listOf(),
    val totalResults: String = "0"
)

fun SearchResult.update(data: SearchResult?): SearchResult {
    if (data == null) return this
    val list = search.toMutableList()
    val new = data.search
    list.addAll(new)
    list.distinctBy { it._id }
    val total = data.totalResults
    return copy(search = list, totalResults = total)
}