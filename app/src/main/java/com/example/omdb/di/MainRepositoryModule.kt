package com.example.omdb.di

import android.content.SharedPreferences
import com.example.omdb.data.local.AppDatabase
import com.example.omdb.data.network.api.contract.OMDbApi
import com.example.omdb.data.repository.MainRepositoryImpl
import com.example.omdb.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MainRepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideMainRepository(
        api: OMDbApi,
        db: AppDatabase,
        sp: SharedPreferences
    ): MainRepository = MainRepositoryImpl(api, db, sp)

}