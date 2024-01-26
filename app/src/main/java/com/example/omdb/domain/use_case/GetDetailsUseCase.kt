package com.example.omdb.domain.use_case

import com.example.omdb.domain.model.Content
import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class GetDetailsUseCase(
    private val repository: MainRepository
) {
    operator fun invoke(
        id: String,
        plot: String
    ): Flow<Resource<Content>> = repository.getDetails(
        id = id,
        plot = plot
    )
}