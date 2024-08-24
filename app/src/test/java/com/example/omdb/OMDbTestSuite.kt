package com.example.omdb

import com.example.omdb.data.DataTestSuite
import com.example.omdb.util.UtilTestSuite
import org.junit.runner.RunWith
import org.junit.runners.Suite

@Suite.SuiteClasses(
    DataTestSuite::class,
    UtilTestSuite::class
)
@RunWith(Suite::class)
class OMDbTestSuite