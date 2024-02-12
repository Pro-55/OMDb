package com.example.omdb.ui.details.full_poster

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.omdb.domain.state.FullPosterScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FullPosterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Global
    private val TAG = FullPosterViewModel::class.java.simpleName
    private var stateValue = FullPosterScreenState(
        url = savedStateHandle["posterUrl"]
    )
    private val _state = MutableLiveData(stateValue)
    val state: LiveData<FullPosterScreenState> = _state
}