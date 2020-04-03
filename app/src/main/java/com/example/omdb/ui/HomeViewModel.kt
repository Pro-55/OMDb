package com.example.omdb.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdb.data.repository.HomeRepositoryImpl
import com.example.omdb.models.Resource
import com.example.omdb.models.SearchResult
import com.example.omdb.models.Status
import com.example.omdb.models.Type
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: HomeRepositoryImpl
) : ViewModel() {

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
        private const val PAGE_SIZE = 10
    }

    private var movieResult = SearchResult()
    private val _movieSearch = MutableLiveData<Resource<SearchResult>>()
    val movieSearch: LiveData<Resource<SearchResult>> = _movieSearch
    private var seriesResult = SearchResult()
    private val _seriesSearch = MutableLiveData<Resource<SearchResult>>()
    val seriesSearch: LiveData<Resource<SearchResult>> = _seriesSearch
    private var episodeResult = SearchResult()
    private val _episodeSearch = MutableLiveData<Resource<SearchResult>>()
    val episodeSearch: LiveData<Resource<SearchResult>> = _episodeSearch

    fun searchMovies(searchText: String, size: Int) {
        if (size <= 0) movieResult = SearchResult()
        val page = if (size <= 0) 1 else size / PAGE_SIZE + 1

        repository.searchMovies(searchText, page).onEach { resourse ->
            var newResource = resourse
            if (resourse.status == Status.SUCCESS) {
                val list = movieResult.search.toMutableList()
                val new = resourse.data?.search ?: listOf()
                list.addAll(new)
                list.distinctBy { it._id }
                val total = resourse.data?.totalResults ?: movieResult.totalResults
                movieResult = movieResult.copy(search = list, totalResults = total)
                newResource = resourse.copy(data = movieResult)
            }
            _movieSearch.postValue(newResource)
        }
            .launchIn(viewModelScope)
    }

    fun searchSeries(searchText: String, size: Int = 0) {
        if (size <= 0) seriesResult = SearchResult()
        val page = if (size <= 0) 1 else size / PAGE_SIZE + 1

        repository.searchSeries(searchText, page).onEach { resourse ->
            var newResource = resourse
            if (resourse.status == Status.SUCCESS) {
                val list = seriesResult.search.toMutableList()
                val new = resourse.data?.search ?: listOf()
                list.addAll(new)
                list.distinctBy { it._id }
                val total = resourse.data?.totalResults ?: seriesResult.totalResults
                seriesResult = seriesResult.copy(search = list, totalResults = total)
                newResource = resourse.copy(data = seriesResult)
            }
            _seriesSearch.postValue(newResource)
        }
            .launchIn(viewModelScope)
    }

    fun searchEpisodes(searchText: String, size: Int = 0) {
        if (size <= 0) episodeResult = SearchResult()
        val page = if (size <= 0) 1 else size / PAGE_SIZE + 1

        repository.searchEpisodes(searchText, page).onEach { resourse ->
            var newResource = resourse
            if (resourse.status == Status.SUCCESS) {
                val list = episodeResult.search.toMutableList()
                val new = resourse.data?.search ?: listOf()
                list.addAll(new)
                list.distinctBy { it._id }
                val total = resourse.data?.totalResults ?: episodeResult.totalResults
                episodeResult = episodeResult.copy(search = list, totalResults = total)
                newResource = resourse.copy(data = episodeResult)
            }
            _episodeSearch.postValue(newResource)
        }
            .launchIn(viewModelScope)
    }

    fun clearSearchData(category: Type) {

        val blankResult = Resource.loading(SearchResult())
        movieResult = SearchResult()
        seriesResult = SearchResult()
        episodeResult = SearchResult()

        when (category) {
            Type.MOVIES -> _movieSearch.postValue(blankResult)
            Type.SERIES -> _seriesSearch.postValue(blankResult)
            Type.EPISODES -> _episodeSearch.postValue(blankResult)
        }
    }

}