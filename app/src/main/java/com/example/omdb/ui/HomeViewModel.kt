package com.example.omdb.ui

import androidx.lifecycle.*
import com.example.omdb.data.repository.contract.HomeRepository
import com.example.omdb.models.Resource
import com.example.omdb.models.SearchResult
import com.example.omdb.models.Type
import com.example.omdb.models.local.EntityUser
import com.example.omdb.models.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: HomeRepository
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

    fun signUp(user: EntityUser) = repository.signUp(user).asLiveData()

    fun getCurrentUser() = repository.getCurrentUser().asLiveData()

    fun searchMovies(searchText: String, size: Int) {
        if (size <= 0) movieResult = SearchResult()
        val page = if (size <= 0) 1 else size / PAGE_SIZE + 1

        repository.searchContent(searchText, page, Type.MOVIE).onEach { resource ->
            var newResource = resource
            if (resource is Resource.Success) {
                movieResult = movieResult.update(resource.data)
                newResource = Resource.Success(data = movieResult)
            }
            _movieSearch.postValue(newResource)
        }
            .launchIn(viewModelScope)
    }

    fun searchSeries(searchText: String, size: Int = 0) {
        if (size <= 0) seriesResult = SearchResult()
        val page = if (size <= 0) 1 else size / PAGE_SIZE + 1

        repository.searchContent(searchText, page, Type.SERIES).onEach { resource ->
            var newResource = resource
            if (resource is Resource.Success) {
                seriesResult = seriesResult.update(resource.data)
                newResource = Resource.Success(data = seriesResult)
            }
            _seriesSearch.postValue(newResource)
        }
            .launchIn(viewModelScope)
    }

    fun getDetails(id: String, plot: String = "short") =
        repository.getDetails(id, plot).asLiveData()

    fun getEpisodes(id: String, season: Int) = repository.getEpisodes(id, season).asLiveData()

    fun clearSearchData(category: Type) {

        val blankResult = Resource.Loading<SearchResult>()
        movieResult = SearchResult()
        seriesResult = SearchResult()

        when (category) {
            Type.MOVIE -> _movieSearch.postValue(blankResult)
            Type.SERIES -> _seriesSearch.postValue(blankResult)
            else -> Unit
        }
    }

}