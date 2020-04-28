package com.example.omdb.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.omdb.data.local.AppDatabase
import com.example.omdb.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences(Constants.OMDB_SHARED_PREFS, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideAppDatabase(application: Application) = AppDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseCrashlytics() = FirebaseCrashlytics.getInstance()

}