package com.example.omdb.di

import android.content.SharedPreferences
import com.example.omdb.data.local.AppDatabase
import com.example.omdb.data.network.api.contract.OMDbApi
import com.example.omdb.data.repository.MainRepositoryImpl
import com.example.omdb.domain.repository.MainRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainRepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        api: OMDbApi,
        db: AppDatabase,
        sp: SharedPreferences,
        auth: FirebaseAuth
    ): MainRepository = MainRepositoryImpl(
        api = api,
        db = db,
        sp = sp,
        auth = auth
    )

}