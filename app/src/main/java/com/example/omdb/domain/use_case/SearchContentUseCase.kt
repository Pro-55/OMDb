package com.example.omdb.domain.use_case

import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.model.SearchResult
import com.example.omdb.domain.model.Type
import com.example.omdb.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class SearchContentUseCase(
    private val repository: MainRepository
) {
    operator fun invoke(
        searchString: String,
        page: Int,
        type: Type
    ): Flow<Resource<SearchResult>> = repository.searchContent(
        searchString = searchString,
        page = page,
        type = type
    )
}