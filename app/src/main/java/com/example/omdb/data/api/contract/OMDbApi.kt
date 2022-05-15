package com.example.omdb.data.api.contract

import com.example.omdb.models.Type
import com.example.omdb.models.network.NetworkContent
import com.example.omdb.models.network.NetworkSeason
import com.example.omdb.models.network.Response
import com.example.omdb.models.network.NetworkSearchResult

interface OMDbApi {

    suspend fun searchContent(
        title: String,
        page: Int,
        type: Type
    ): Response<NetworkSearchResult>

    suspend fun getDetails(
        id: String,
        plot: String
    ): Response<NetworkContent>

    suspend fun getEpisodes(
        id: String,
        season: Int
    ): Response<NetworkSeason>

}