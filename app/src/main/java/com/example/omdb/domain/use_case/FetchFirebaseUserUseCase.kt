package com.example.omdb.domain.use_case

import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.repository.MainRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

class FetchFirebaseUserUseCase(
    private val repository: MainRepository
) {
    operator fun invoke(
        credential: AuthCredential?
    ): Flow<Resource<FirebaseUser>> = repository.fetchFirebaseUser(credential = credential)
}