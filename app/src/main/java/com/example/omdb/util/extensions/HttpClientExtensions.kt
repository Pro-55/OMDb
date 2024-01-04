package com.example.omdb.util.extensions

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get

suspend inline fun <reified T> HttpClient.get(
    block: HttpRequestBuilder.() -> Unit
): T = get(block).body<T>()