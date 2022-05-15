package com.example.omdb.di

import com.example.omdb.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import javax.inject.Singleton
import kotlinx.serialization.json.Json as KotlinXJson

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient = HttpClient(CIO) {
        install(HttpTimeout) {
            connectTimeoutMillis = 1000
            requestTimeoutMillis = 5000
        }
        install(Logging) { level = if (BuildConfig.DEBUG) LogLevel.BODY else LogLevel.NONE }
        install(JsonFeature) {
            serializer = KotlinxSerializer(KotlinXJson { ignoreUnknownKeys = true })
        }
    }
}