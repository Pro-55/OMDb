package com.example.omdb.di

import com.example.omdb.domain.repository.MainRepository
import com.example.omdb.domain.use_case.FetchFirebaseUserUseCase
import com.example.omdb.domain.use_case.GetCurrentUserUseCase
import com.example.omdb.domain.use_case.GetDetailsUseCase
import com.example.omdb.domain.use_case.GetEpisodesUseCase
import com.example.omdb.domain.use_case.GetGreetingUseCase
import com.example.omdb.domain.use_case.GetSignUpStatusUseCase
import com.example.omdb.domain.use_case.SearchContentUseCase
import com.example.omdb.domain.use_case.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetSignUpStatusUseCase(
        repository: MainRepository
    ): GetSignUpStatusUseCase = GetSignUpStatusUseCase(repository = repository)

    @ViewModelScoped
    @Provides
    fun provideFetchFirebaseUserUseCase(
        repository: MainRepository
    ): FetchFirebaseUserUseCase = FetchFirebaseUserUseCase(repository = repository)

    @ViewModelScoped
    @Provides
    fun provideSignUpUseCase(
        repository: MainRepository
    ): SignUpUseCase = SignUpUseCase(repository = repository)

    @ViewModelScoped
    @Provides
    fun provideGetCurrentUserUseCase(
        repository: MainRepository
    ): GetCurrentUserUseCase = GetCurrentUserUseCase(repository = repository)

    @ViewModelScoped
    @Provides
    fun provideGetGreetingUseCase(
        repository: MainRepository
    ): GetGreetingUseCase = GetGreetingUseCase(repository = repository)

    @ViewModelScoped
    @Provides
    fun provideSearchContentUseCase(
        repository: MainRepository
    ): SearchContentUseCase = SearchContentUseCase(repository = repository)

    @ViewModelScoped
    @Provides
    fun provideGetDetailsUseCase(
        repository: MainRepository
    ): GetDetailsUseCase = GetDetailsUseCase(repository = repository)

    @ViewModelScoped
    @Provides
    fun provideGetEpisodesUseCase(
        repository: MainRepository
    ): GetEpisodesUseCase = GetEpisodesUseCase(repository = repository)

}