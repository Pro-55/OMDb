package com.example.omdb.di.home

import com.example.omdb.data.network.api.OMDbApi
import com.example.omdb.data.repository.HomeRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
object HomeRepositoryModule {

    @HomeScope
    @Provides
    fun provideHomeRepository(api: OMDbApi) = HomeRepositoryImpl(api)

}