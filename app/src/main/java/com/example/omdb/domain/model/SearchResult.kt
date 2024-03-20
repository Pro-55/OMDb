package com.example.omdb.domain.model

data class SearchResult(
    val search: List<ShortContent> = listOf(),
    val totalResults: String = "0",
    val total: Int = 0
)

fun SearchResult.update(data: SearchResult?): SearchResult {
    if (data == null) return this
    val list = search.toMutableList()
    val new = data.search
    list.addAll(new)
    list.distinctBy { it._id }
    return copy(
        search = list,
        totalResults = data.totalResults,
        total = data.total
    )
}