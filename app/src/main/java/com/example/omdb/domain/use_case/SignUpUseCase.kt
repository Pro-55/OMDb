package com.example.omdb.domain.use_case

import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.model.User
import com.example.omdb.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow

class SignUpUseCase(
    private val repository: MainRepository
) {
    operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        profileUrl: String?
    ): Flow<Resource<User>> = repository.signUp(
        firstName = firstName,
        lastName = lastName,
        email = email,
        profileUrl = profileUrl
    )
}