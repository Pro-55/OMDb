package com.example.omdb.domain.use_case

import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.model.User
import com.example.omdb.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentUserUseCase(
    private val repository: MainRepository
) {
    operator fun invoke(): Flow<Resource<User>> = repository.getCurrentUser()
}