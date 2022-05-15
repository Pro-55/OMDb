package com.example.omdb.di

import com.example.omdb.data.api.contract.OMDbApi
import com.example.omdb.data.api.impl.OMDbApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideOMDbApi(client: HttpClient): OMDbApi = OMDbApiImpl(client)

}