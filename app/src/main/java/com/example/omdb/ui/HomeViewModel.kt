package com.example.omdb.ui

import androidx.lifecycle.*
import com.example.omdb.data.repository.impl.HomeRepositoryImpl
import com.example.omdb.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: HomeRepositoryImpl
) : ViewModel() {

    // Global
    private val TAG = HomeViewModel::class.java.simpleName
    private val PAGE_SIZE = 10
    private var movieResult = SearchResult()
    private val _movieSearch = MutableLiveData<Resource<SearchResult>>()
    val movieSearch: LiveData<Resource<SearchResult>> = _movieSearch
    private var seriesResult = SearchResult()
    private val _seriesSearch = MutableLiveData<Resource<SearchResult>>()
    val seriesSearch: LiveData<Resource<SearchResult>> = _seriesSearch

    fun signUp(user: User) = repository.signUp(user)

    fun searchMovies(searchText: String, size: Int) {
        if (size <= 0) movieResult = SearchResult()
        val page = if (size <= 0) 1 else size / PAGE_SIZE + 1

        repository.searchMovies(searchText, page).onEach { resource ->
            var newResource = resource
            if (resource.status == Status.SUCCESS) {
                val list = movieResult.search.toMutableList()
                val new = resource.data?.search ?: listOf()
                list.addAll(new)
                list.distinctBy { it._id }
                val total = resource.data?.totalResults ?: movieResult.totalResults
                movieResult = movieResult.copy(search = list, totalResults = total)
                newResource = resource.copy(data = movieResult)
            }
            _movieSearch.postValue(newResource)
        }
            .launchIn(viewModelScope)
    }

    fun searchSeries(searchText: String, size: Int = 0) {
        if (size <= 0) seriesResult = SearchResult()
        val page = if (size <= 0) 1 else size / PAGE_SIZE + 1

        repository.searchSeries(searchText, page).onEach { resource ->
            var newResource = resource
            if (resource.status == Status.SUCCESS) {
                val list = seriesResult.search.toMutableList()
                val new = resource.data?.search ?: listOf()
                list.addAll(new)
                list.distinctBy { it._id }
                val total = resource.data?.totalResults ?: seriesResult.totalResults
                seriesResult = seriesResult.copy(search = list, totalResults = total)
                newResource = resource.copy(data = seriesResult)
            }
            _seriesSearch.postValue(newResource)
        }
            .launchIn(viewModelScope)
    }

    fun getDetails(id: String, plot: String = "short") =
        repository.getDetails(id, plot).asLiveData()

    fun getEpisodes(id: String, season: Int) = repository.getEpisodes(id, season).asLiveData()

    fun clearSearchData(category: Type) {

        val blankResult = Resource.loading(SearchResult())
        movieResult = SearchResult()
        seriesResult = SearchResult()

        when (category) {
            Type.MOVIES -> _movieSearch.postValue(blankResult)
            Type.SERIES -> _seriesSearch.postValue(blankResult)
        }
    }

}