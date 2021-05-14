package com.example.omdb.di

import android.content.SharedPreferences
import com.example.omdb.data.api.OMDbApi
import com.example.omdb.data.local.AppDatabase
import com.example.omdb.data.repository.impl.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object HomeRepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideHomeRepository(api: OMDbApi, db: AppDatabase, sp: SharedPreferences) =
        HomeRepositoryImpl(api, db, sp)

}