package com.example.omdb.ui.details.ratings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RatingsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Global
    private val TAG = RatingsViewModel::class.java.simpleName

    init {
        // TODO: something here
    }
}