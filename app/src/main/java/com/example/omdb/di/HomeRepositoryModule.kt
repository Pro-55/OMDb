package com.example.omdb.di

import com.example.omdb.data.api.OMDbApi
import com.example.omdb.data.local.AppDatabase
import com.example.omdb.data.repository.impl.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object HomeRepositoryModule {

    @ActivityRetainedScoped
    @Provides
    fun provideHomeRepository(api: OMDbApi, db: AppDatabase) =
        HomeRepositoryImpl(api, db)

}