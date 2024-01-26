package com.example.omdb.domain.use_case

import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.model.Season
import com.example.omdb.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class GetEpisodesUseCase(
    private val repository: MainRepository
) {
    operator fun invoke(
        id: String,
        season: Int
    ): Flow<Resource<Season>> = repository.getEpisodes(
        id = id,
        season = season
    )
}