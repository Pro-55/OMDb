package com.example.omdb.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.omdb.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(
        application: Application
    ): SharedPreferences = application.getSharedPreferences(
        Constants.OMDB_SHARED_PREFS,
        Context.MODE_PRIVATE
    )

}