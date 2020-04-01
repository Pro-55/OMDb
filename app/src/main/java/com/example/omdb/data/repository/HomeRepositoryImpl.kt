package com.example.omdb.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.omdb.BuildConfig.ApiKey
import com.example.omdb.data.contract.HomeRepository
import com.example.omdb.data.network.api.OMDbApi
import com.example.omdb.models.FullData
import com.example.omdb.models.Resource
import com.example.omdb.models.SearchResult
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

    override fun searchMovies(searchString: String, page: Int): LiveData<Resource<SearchResult>> {
        val liveData = MutableLiveData<Resource<SearchResult>>()
        api.searchContent(apiKey = ApiKey, title = searchString, type = "movie", page = page)
            .enqueue(object : Callback<SearchResult> {
                override fun onResponse(
                    call: Call<SearchResult>,
                    response: Response<SearchResult>
                ) {
                    liveData.postValue(Resource.success(response.body()))
                }

                override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                    t.printStackTrace()
                    liveData.postValue(Resource.error(t.message ?: ""))
                }
            })

        return liveData
    }

    override fun searchSeries(searchString: String, page: Int): LiveData<Resource<SearchResult>> {
        val liveData = MutableLiveData<Resource<SearchResult>>()
        api.searchContent(apiKey = ApiKey, title = searchString, type = "series", page = page)
            .enqueue(object : Callback<SearchResult> {
                override fun onResponse(
                    call: Call<SearchResult>,
                    response: Response<SearchResult>
                ) {
                    liveData.postValue(Resource.success(response.body()))
                }

                override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                    t.printStackTrace()
                    liveData.postValue(Resource.error(t.message ?: ""))
                }
            })

        return liveData
    }

    override fun searchEpisodes(searchString: String, page: Int): LiveData<Resource<SearchResult>> {
        val liveData = MutableLiveData<Resource<SearchResult>>()
        api.searchContent(apiKey = ApiKey, title = searchString, type = "episode", page = page)
            .enqueue(object : Callback<SearchResult> {
                override fun onResponse(
                    call: Call<SearchResult>,
                    response: Response<SearchResult>
                ) {
                    liveData.postValue(Resource.success(response.body()))
                }

                override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                    t.printStackTrace()
                    liveData.postValue(Resource.error(t.message ?: ""))
                }
            })

        return liveData
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