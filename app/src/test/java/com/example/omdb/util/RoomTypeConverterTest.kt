package com.example.omdb.util

import com.example.omdb.domain.model.Type
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RoomTypeConverterTest {

    @Test
    fun `test room converter for from value to type`() = runTest {
        val movieType = RoomTypeConverter().toContentType(value = 0)
        assert(movieType == Type.MOVIE)

        val seriesType = RoomTypeConverter().toContentType(value = 1)
        assert(seriesType == Type.SERIES)

        val episodeType = RoomTypeConverter().toContentType(value = 2)
        assert(episodeType == Type.EPISODES)

        val randomType = RoomTypeConverter().toContentType(value = null)
        assert(randomType == Type.MOVIE)
    }

    @Test
    fun `test room converter for from type to value`() = runTest {
        val movieValue = RoomTypeConverter().fromContentType(contentType = Type.MOVIE)
        assert(movieValue == 0)

        val seriesValue = RoomTypeConverter().fromContentType(contentType = Type.SERIES)
        assert(seriesValue == 1)

        val episodeValue = RoomTypeConverter().fromContentType(contentType = Type.EPISODES)
        assert(episodeValue == 2)

        val randomValue = RoomTypeConverter().fromContentType(contentType = null)
        assert(randomValue == 0)
    }
}