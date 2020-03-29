package com.example.omdb.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object OkHttpModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        val clientBuilder = OkHttpClient().newBuilder()
            .connectTimeout(5, TimeUnit.SECONDS)
            // Do consider increasing read/write timeouts if backend service is running long queries
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)

//        clientBuilder.addNetworkInterceptor(logging)
        return clientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        // OkHttp Logging Interceptor
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

}