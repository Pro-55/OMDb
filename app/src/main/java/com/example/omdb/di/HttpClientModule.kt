package com.example.omdb.di

import com.example.omdb.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton
import kotlinx.serialization.json.Json as KotlinXJson

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient = HttpClient(OkHttp) {
        defaultRequest { url(BuildConfig.BaseUrl) }
        install(HttpTimeout) {
            connectTimeoutMillis = 1000
            requestTimeoutMillis = 5000
        }
        install(Logging) { level = if (BuildConfig.DEBUG) LogLevel.BODY else LogLevel.NONE }
        install(ContentNegotiation) {
            json(KotlinXJson { ignoreUnknownKeys = true })
        }
    }
}