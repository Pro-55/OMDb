package com.example.omdb.di

import com.example.omdb.BuildConfig.ApiKey
import com.example.omdb.util.dummy_data.emptySearchDummyData
import com.example.omdb.util.dummy_data.movieDetailsDummyData
import com.example.omdb.util.dummy_data.moviesSearchDummyData
import com.example.omdb.util.dummy_data.seriesDetailsDummyData
import com.example.omdb.util.dummy_data.seriesSearchDummyData
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.net.UnknownHostException

val testEngine = MockEngine { request ->
    when (val path = request.url.fullPath) {
        "?apiKey=${ApiKey}&s=Batman&type=movie&page=1" -> respond(
            content = moviesSearchDummyData,
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        )
        "?apiKey=${ApiKey}&s=Robin&type=movie&page=1" -> respond(
            content = emptySearchDummyData,
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        )
        "?apiKey=${ApiKey}&s=Friends&type=series&page=1" -> respond(
            content = seriesSearchDummyData,
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        )
        "?apiKey=${ApiKey}&s=Enemies&type=series&page=1" -> respond(
            content = emptySearchDummyData,
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        )
        "?apiKey=${ApiKey}&s=B&type=episodes&page=0" -> throw UnknownHostException()
        "?apiKey=${ApiKey}&i=tt0372784&plot=short" -> respond(
            content = movieDetailsDummyData,
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        )
        "?apiKey=${ApiKey}&i=tt0108778&plot=short" -> respond(
            content = seriesDetailsDummyData,
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        )
        else -> throw Exception("Unknown path => $path")
    }
}

val testClient = HttpClient(testEngine) {
    install(HttpTimeout) {
        connectTimeoutMillis = 1000
        requestTimeoutMillis = 5000
    }
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
}