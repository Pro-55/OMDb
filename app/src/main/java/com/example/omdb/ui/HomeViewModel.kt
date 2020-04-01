package com.example.omdb.ui

import androidx.lifecycle.ViewModel
import com.example.omdb.data.repository.HomeRepositoryImpl
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: HomeRepositoryImpl
) : ViewModel() {

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }

    fun searchMovies(searchText: String, page: Int) = repository.searchMovies(searchText, page)

    fun searchSeries(searchText: String, page: Int) = repository.searchSeries(searchText, page)

    fun searchEpisodes(searchText: String, page: Int) = repository.searchEpisodes(searchText, page)

}