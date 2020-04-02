package com.example.omdb.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.omdb.data.repository.HomeRepositoryImpl
import com.example.omdb.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: HomeRepositoryImpl
) : ViewModel() {

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
        private val PAGE_SIZE = 10
    }

    private val _movieSearch = MutableLiveData<Resource<SearchResult>>()
    val movieSearch = _movieSearch
    private val _seriesSearch = MutableLiveData<Resource<SearchResult>>()
    val seriesSearch = _seriesSearch
    private val _episodeSearch = MutableLiveData<Resource<SearchResult>>()
    val episodeSearch = _episodeSearch

    fun searchMovies(searchText: String, size: Int = 0) {
        val page = if (size <= 0) 1 else size / PAGE_SIZE + 1
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchMovies(searchText, page)
                .asFlow()
                .collect {
                    var resource = it
                    if (resource.status == Status.SUCCESS) {
                        val oldResult = resource.data
                        val old = _movieSearch.value?.data?.search ?: listOf()
                        val new = oldResult?.search ?: listOf()
                        val list = mutableListOf<ShortData>()
                        list.addAll(old)
                        list.addAll(new)
                        list.distinctBy { data -> data._id }
                        val newResult = oldResult?.copy(search = list)
                        resource = it.copy(data = newResult)
                    }
                    _movieSearch.postValue(resource)
                }
        }
    }

    fun searchSeries(searchText: String, size: Int = 0) {
        val page = if (size <= 0) 1 else size / PAGE_SIZE + 1
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchSeries(searchText, page)
                .asFlow()
                .collect {
                    var resource = it
                    if (resource.status == Status.SUCCESS) {
                        val oldResult = resource.data
                        val old = _seriesSearch.value?.data?.search ?: listOf()
                        val new = oldResult?.search ?: listOf()
                        val list = mutableListOf<ShortData>()
                        list.addAll(old)
                        list.addAll(new)
                        list.distinctBy { data -> data._id }
                        val newResult = oldResult?.copy(search = list)
                        resource = it.copy(data = newResult)
                    }
                    _seriesSearch.postValue(resource)
                }
        }
    }

    fun searchEpisodes(searchText: String, size: Int = 0) {
        val page = if (size <= 0) 1 else size / PAGE_SIZE + 1
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchEpisodes(searchText, page).asFlow()
                .collect {
                    var resource = it
                    if (resource.status == Status.SUCCESS) {
                        val oldResult = resource.data
                        val old = _episodeSearch.value?.data?.search ?: listOf()
                        val new = oldResult?.search ?: listOf()
                        val list = mutableListOf<ShortData>()
                        list.addAll(old)
                        list.addAll(new)
                        list.distinctBy { data -> data._id }
                        val newResult = oldResult?.copy(search = list)
                        resource = it.copy(data = newResult)
                    }
                    _episodeSearch.postValue(resource)
                }
        }
    }

    fun clearSearchData(category: Type) {
        val blankResult = Resource.loading(SearchResult(response = null, error = null))
        when (category) {
            Type.MOVIES -> _movieSearch.postValue(blankResult)
            Type.SERIES -> _seriesSearch.postValue(blankResult)
            Type.EPISODES -> _episodeSearch.postValue(blankResult)
        }

    }

}