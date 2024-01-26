package com.example.omdb.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.model.SearchResult
import com.example.omdb.domain.model.Type
import com.example.omdb.domain.model.update
import com.example.omdb.domain.use_case.SearchContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val searchContentUseCase: SearchContentUseCase
) : ViewModel() {

    // Global
    private val TAG = SearchViewModel::class.java.simpleName
    private val PAGE_SIZE = 10
    private var movieResult = SearchResult()
    private val _movieSearch = MutableLiveData<Resource<SearchResult>>()
    val movieSearch: LiveData<Resource<SearchResult>> = _movieSearch
    private var seriesResult = SearchResult()
    private val _seriesSearch = MutableLiveData<Resource<SearchResult>>()
    val seriesSearch: LiveData<Resource<SearchResult>> = _seriesSearch

    fun searchMovies(searchText: String, size: Int) {
        if (size <= 0) movieResult = SearchResult()
        val page = if (size <= 0) 1 else size / PAGE_SIZE + 1

        searchContentUseCase(
            searchString = searchText,
            page = page,
            type = Type.MOVIE
        )
            .onEach { resource ->
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

        searchContentUseCase(
            searchString = searchText,
            page = page,
            type = Type.SERIES
        )
            .onEach { resource ->
                var newResource = resource
                if (resource is Resource.Success) {
                    seriesResult = seriesResult.update(resource.data)
                    newResource = Resource.Success(data = seriesResult)
                }
                _seriesSearch.postValue(newResource)
            }
            .launchIn(viewModelScope)
    }

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