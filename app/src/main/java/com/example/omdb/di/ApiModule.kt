package com.example.omdb.di

import com.example.omdb.data.network.api.contract.OMDbApi
import com.example.omdb.data.network.api.impl.OMDbApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideOMDbApi(
        client: HttpClient
    ): OMDbApi = OMDbApiImpl(client = client)

}