package com.example.omdb.domain.use_case

import com.example.omdb.domain.repository.MainRepository
import java.util.Calendar

class GetGreetingUseCase(
    private val repository: MainRepository,
) {
    suspend operator fun invoke(
        userName: String?
    ): String = repository.getGreeting(
        userName = userName,
        calendar = Calendar.getInstance()
    )
}