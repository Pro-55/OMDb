package com.example.omdb.di

import com.example.omdb.data.api.OMDbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideOMDbApi(retrofit: Retrofit): OMDbApi {
        return retrofit.create(OMDbApi::class.java)
    }

}