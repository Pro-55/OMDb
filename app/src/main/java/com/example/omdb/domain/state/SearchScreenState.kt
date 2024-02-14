package com.example.omdb.domain.state

import com.example.omdb.domain.model.SearchResult
import com.example.omdb.domain.model.ShortContent
import com.example.omdb.domain.model.Type

data class SearchScreenState(
    val category: Type = Type.MOVIE,
    val query: String = "",
    val result: SearchResult = SearchResult(),
    val peekContent: ShortContent? = null,
    val shouldPeek: Boolean = false
)