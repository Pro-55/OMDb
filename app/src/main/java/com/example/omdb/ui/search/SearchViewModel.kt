package com.example.omdb.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.model.SearchResult
import com.example.omdb.domain.model.ShortContent
import com.example.omdb.domain.model.Type
import com.example.omdb.domain.model.update
import com.example.omdb.domain.state.SearchScreenState
import com.example.omdb.domain.use_case.SearchContentUseCase
import com.example.omdb.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchContentUseCase: SearchContentUseCase
) : ViewModel() {

    // Global
    private val TAG = SearchViewModel::class.java.simpleName
    private val _queryFlow = MutableStateFlow("")
    private var stateValue = SearchScreenState(
        category = savedStateHandle["category"] ?: Type.MOVIE
    )
    private val _state = MutableLiveData(stateValue)
    val state: LiveData<SearchScreenState> = _state
    var isLoading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {
            _queryFlow.debounce(timeoutMillis = 300L)
                .collectLatest {
                    if (it.isNotEmpty()) searchContent(shouldClearResults = true)
                }
        }
    }

    fun onSearchQueryUpdated(query: String) {
        stateValue = stateValue.copy(query = query)
        _state.value = stateValue
        _queryFlow.update { query }
    }

    fun onClearSearchQuery() {
        stateValue = stateValue.copy(
            query = "",
            result = SearchResult()
        )
        _state.value = stateValue
        _queryFlow.update { "" }
    }

    fun searchNow() {
        searchContent(shouldClearResults = true)
    }

    fun loadMore() {
        searchContent(shouldClearResults = false)
    }

    fun onHold(peekContent: ShortContent) {
        stateValue = stateValue.copy(
            peekContent = peekContent,
            shouldPeek = true
        )
        _state.postValue(stateValue)
    }

    fun onRelease() {
        viewModelScope.launch {
            stateValue = stateValue.copy(shouldPeek = false)
            _state.value = stateValue
        }
    }

    private fun searchContent(shouldClearResults: Boolean) {
        if (shouldClearResults) {
            stateValue = stateValue.copy(result = SearchResult())
            _state.value = stateValue
        }
        val size = stateValue.result.search.size
        searchContentUseCase(
            query = stateValue.query.trim(),
            page = if (size <= 0) 1 else size / Constants.PAGE_SIZE + 1,
            type = stateValue.category
        )
            .onEach {
                when (it) {
                    is Resource.Loading -> isLoading = true
                    is Resource.Success -> {
                        stateValue = stateValue.copy(
                            result = stateValue.result.update(data = it.data)
                        )
                        _state.value = stateValue
                        isLoading = false
                    }
                    is Resource.Error -> {
                        error = it.msg
                        isLoading = false
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun resetError() {
        error = null
    }
}