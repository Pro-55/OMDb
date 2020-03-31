package com.example.omdb.ui

import androidx.lifecycle.*
import com.example.omdb.data.repository.HomeRepositoryImpl
import com.example.omdb.models.Resource
import com.example.omdb.models.SearchResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: HomeRepositoryImpl
) : ViewModel() {

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
        private const val PAGE_SIZE = 10
    }

    init {
        getData()
    }

    private val _data = MutableLiveData<Resource<SearchResult>>()
    val data: LiveData<Resource<SearchResult>> = _data


    private fun getData() {
        viewModelScope.launch {
            repository.searchMovies("bat", 1).asFlow().collect { _data.postValue(it) }
        }
    }

}