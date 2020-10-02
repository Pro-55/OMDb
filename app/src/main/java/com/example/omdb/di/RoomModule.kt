package com.example.omdb.di

import android.app.Application
import com.example.omdb.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideAppDatabase(application: Application) = AppDatabase.getInstance(application)

}