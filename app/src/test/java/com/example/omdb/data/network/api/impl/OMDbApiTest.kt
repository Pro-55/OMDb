package com.example.omdb.data.network.api.impl

import com.example.omdb.data.network.model.Response
import com.example.omdb.domain.model.Type
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.example.omdb.di.testApi as api

class OMDbApiTest {

    @Test
    fun `search content success movies with data test`() = runTest {
        val result = api.searchContent(
            title = "Batman",
            page = 1,
            type = Type.MOVIE
        )
        assert(result is Response.Success)

        val data = (result as Response.Success).data
        assert(data != null)

        val size = data!!.search?.size ?: 0
        assert(size > 0)
    }

    @Test
    fun `search content success movies without data test`() = runTest {
        val result = api.searchContent(
            title = "Robin",
            page = 1,
            type = Type.MOVIE
        )
        assert(result is Response.Success)

        val data = (result as Response.Success).data
        assert(data != null)

        val size = data!!.search?.size ?: 0
        assert(size == 0)
    }

    @Test
    fun `search content success series with data test`() = runTest {
        val result = api.searchContent(
            title = "Friends",
            page = 1,
            type = Type.SERIES
        )
        assert(result is Response.Success)

        val data = (result as Response.Success).data
        assert(data != null)

        val size = data!!.search?.size ?: 0
        assert(size > 0)
    }

    @Test
    fun `search content success series without data test`() = runTest {
        val result = api.searchContent(
            title = "Enemies",
            page = 1,
            type = Type.SERIES
        )
        assert(result is Response.Success)

        val data = (result as Response.Success).data
        assert(data != null)

        val size = data!!.search?.size ?: 0
        assert(size == 0)
    }

    @Test
    fun `get movie details test`() = runTest {
        val result = api.getDetails(
            id = "tt0372784",
            plot = "short"
        )
        assert(result is Response.Success)

        val data = (result as Response.Success).data
        assert(data != null)

        val size = data!!.seasons?.toIntOrNull() ?: 0
        assert(size == 0)

        assert(Type.MOVIE.toString() == data.type)
    }

    @Test
    fun `get series details test`() = runTest {
        val result = api.getDetails(
            id = "tt0108778",
            plot = "short"
        )
        assert(result is Response.Success)

        val data = (result as Response.Success).data
        assert(data != null)

        val size = data!!.seasons?.toIntOrNull() ?: 0
        assert(size > 0)

        assert(Type.SERIES.toString() == data.type)
    }

    @Test
    fun `test unknown host exception`() = runTest {
        val result = api.searchContent(
            title = "B",
            page = 0,
            type = Type.EPISODES
        )
        assert(result is Response.UnknownHostException)
    }

    @Test
    fun `test unknown exception`() = runTest {
        val result = api.searchContent(
            title = "B",
            page = 5,
            type = Type.EPISODES
        )
        assert(result is Response.UnknownException)
    }
}