package com.example.omdb.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.omdb.BuildConfig.ApiKey
import com.example.omdb.data.contract.HomeRepository
import com.example.omdb.data.network.api.OMDbApi
import com.example.omdb.models.FullData
import com.example.omdb.models.Resource
import com.example.omdb.models.SearchResult
import com.example.omdb.util.extensions.resourceFlow
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    override fun searchEpisodes(searchString: String, page: Int): Flow<Resource<SearchResult>> {
        return resourceFlow {
            val result = api.searchContent(
                apiKey = ApiKey,
                title = searchString,
                type = "episode",
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

    override fun getMovieDetails(id: String): LiveData<Resource<FullData>> {
        val liveData = MutableLiveData<Resource<FullData>>()
        api.getMovieDetails(ApiKey, id).enqueue(object : Callback<FullData> {
            override fun onResponse(call: Call<FullData>, response: Response<FullData>) {
                liveData.postValue(Resource.success(response.body()))
            }

            override fun onFailure(call: Call<FullData>, t: Throwable) {
                t.printStackTrace()
                liveData.postValue(Resource.error(t.message ?: ""))
            }
        })

        return liveData
    }

}