package com.example.omdb.data

import com.example.omdb.data.network.api.impl.OMDbApiTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@Suite.SuiteClasses(
    OMDbApiTest::class
)
@RunWith(Suite::class)
class DataTestSuite