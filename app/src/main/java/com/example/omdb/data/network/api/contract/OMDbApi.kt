package com.example.omdb.data.network.api.contract

import com.example.omdb.data.network.model.NetworkContent
import com.example.omdb.data.network.model.NetworkSearchResult
import com.example.omdb.data.network.model.NetworkSeason
import com.example.omdb.data.network.model.Response
import com.example.omdb.domain.model.Type

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