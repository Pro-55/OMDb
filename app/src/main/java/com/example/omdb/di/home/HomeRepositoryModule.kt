package com.example.omdb.di.home

import com.example.omdb.data.local.AppDatabase
import com.example.omdb.data.network.api.OMDbApi
import com.example.omdb.data.repository.impl.HomeRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
object HomeRepositoryModule {

    @HomeScope
    @Provides
    fun provideHomeRepository(api: OMDbApi, db: AppDatabase) =
        HomeRepositoryImpl(api, db)

}