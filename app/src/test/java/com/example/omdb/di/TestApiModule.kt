package com.example.omdb.di

import com.example.omdb.data.network.api.impl.OMDbApiImpl

val testApi = OMDbApiImpl(
    client = testClient
)