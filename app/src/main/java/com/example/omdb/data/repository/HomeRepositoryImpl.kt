package com.example.omdb.data.repository

import com.example.omdb.BuildConfig.ApiKey
import com.example.omdb.data.contract.HomeRepository
import com.example.omdb.data.network.api.OMDbApi
import com.example.omdb.models.FullData
import com.example.omdb.models.Resource
import com.example.omdb.models.SearchResult
import com.example.omdb.models.Season
import com.example.omdb.util.extensions.resourceFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: OMDbApi
) : HomeRepository {

    companion object {
        private val TAG = HomeRepositoryImpl::class.java.simpleName
    }

    override fun searchMovies(searchString: String, page: Int): Flow<Resource<SearchResult>> {
        return resourceFlow {
            val result = api.searchContent(
                apiKey = ApiKey,
                title = searchString,
                type = "movie",
                page = page
            )

            if (result.isSuccessful) {
                val data = result.body() ?: SearchResult()
                emit(Resource.success(data))
            } else {
                val msg = result.message()
                emit(Resource.error(msg))
            }
        }
    }

    override fun searchSeries(searchString: String, page: Int): Flow<Resource<SearchResult>> {
        return resourceFlow {
            val result = api.searchContent(
                apiKey = ApiKey,
                title = searchString,
                type = "series",
                page = page
            )

            if (result.isSuccessful) {
                val data = result.body() ?: SearchResult()
                emit(Resource.success(data))
            } else {
                val msg = result.message()
                emit(Resource.error(msg))
            }
        }
    }

    override fun getDetails(id: String, plot: String): Flow<Resource<FullData>> {
        return resourceFlow {
            val result = api.getDetails(ApiKey, id, plot)

            if (result.isSuccessful) {
                val data = result.body()
                if (data != null) emit(Resource.success(data))
                else emit(Resource.error("Something Went wrong!"))
            } else {
                val msg = result.message()
                emit(Resource.error(msg))
            }
        }
    }

    override fun getEpisodes(id: String, season: Int): Flow<Resource<Season>> {
        return resourceFlow {
            val result = api.getEpisodes(ApiKey, id, season)

            if (result.isSuccessful) {
                val data = result.body()
                if (data != null) emit(Resource.success(data))
                else emit(Resource.error("Something Went wrong!"))
            } else {
                val msg = result.message()
                emit(Resource.error(msg))
            }
        }
    }

}