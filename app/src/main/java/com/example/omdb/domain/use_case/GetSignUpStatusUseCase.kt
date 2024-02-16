package com.example.omdb.domain.use_case

import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class GetSignUpStatusUseCase(
    private val repository: MainRepository
) {
    operator fun invoke(): Flow<Resource<Boolean>> = repository.getSignUpStatus()
}