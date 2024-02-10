package com.example.omdb.ui.details.full_poster

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FullPosterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Global
    private val TAG = FullPosterViewModel::class.java.simpleName

    init {
        // TODO: something here
    }
}