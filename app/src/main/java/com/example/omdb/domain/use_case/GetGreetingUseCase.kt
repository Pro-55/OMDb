package com.example.omdb.domain.use_case

import com.example.omdb.domain.repository.MainRepository

class GetGreetingUseCase(
    private val repository: MainRepository,
) {
    suspend operator fun invoke(
        userName: String?
    ): String = repository.getGreeting(userName = userName)
}