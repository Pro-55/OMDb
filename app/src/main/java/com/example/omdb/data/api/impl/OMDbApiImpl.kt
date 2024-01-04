package com.example.omdb.data.api.impl

import com.example.omdb.BuildConfig.ApiKey
import com.example.omdb.data.api.contract.OMDbApi
import com.example.omdb.models.Type
import com.example.omdb.models.network.NetworkContent
import com.example.omdb.models.network.NetworkSearchResult
import com.example.omdb.models.network.NetworkSeason
import com.example.omdb.models.network.Response
import com.example.omdb.util.extensions.get
import com.example.omdb.util.wrappers.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter

class OMDbApiImpl(
    private val client: HttpClient
) : OMDbApi {

    override suspend fun searchContent(
        title: String,
        page: Int,
        type: Type
    ): Response<NetworkSearchResult> = safeCall {
        val response = client.get<NetworkSearchResult> {
            parameter("apiKey", ApiKey)
            parameter("s", title)
            parameter("type", type)
            parameter("page", page)
        }
        Response.Success(response)
    }

    override suspend fun getDetails(
        id: String,
        plot: String
    ): Response<NetworkContent> = safeCall {
        val response = client.get<NetworkContent> {
            parameter("apiKey", ApiKey)
            parameter("i", id)
            parameter("plot", plot)
        }
        Response.Success(response)
    }

    override suspend fun getEpisodes(
        id: String,
        season: Int
    ): Response<NetworkSeason> = safeCall {
        val response = client.get<NetworkSeason> {
            parameter("apiKey", ApiKey)
            parameter("i", id)
            parameter("season", season)
        }
        Response.Success(response)
    }
}