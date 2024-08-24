package com.example.omdb.util

import com.example.omdb.util.extensions.DateTimeExtensionsTest
import com.example.omdb.util.extensions.ValidationExtensionsTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@Suite.SuiteClasses(
    DateTimeExtensionsTest::class,
    ValidationExtensionsTest::class,
    RoomTypeConverterTest::class
)
@RunWith(Suite::class)
class UtilTestSuite