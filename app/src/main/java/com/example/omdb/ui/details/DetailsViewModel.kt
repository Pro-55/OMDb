package com.example.omdb.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdb.domain.model.Content
import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.use_case.GetDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getDetailsUseCase: GetDetailsUseCase
) : ViewModel() {

    // Global
    private val TAG = DetailsViewModel::class.java.simpleName
    private val _content = MutableLiveData<Resource<Content>>()
    val content: LiveData<Resource<Content>> = _content

    fun getDetails(
        id: String,
        plot: String = "short"
    ) {
        getDetailsUseCase(
            id = id,
            plot = plot
        )
            .onEach { _content.postValue(it) }
            .launchIn(viewModelScope)
    }
}