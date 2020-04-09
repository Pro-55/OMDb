package com.example.omdb.di.home

import com.example.omdb.data.network.api.OMDbApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object HomeApiModule {

    @HomeScope
    @Provides
    fun provideOMDbApi(retrofit: Retrofit): OMDbApi {
        return retrofit.create(OMDbApi::class.java)
    }

}