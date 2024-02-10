package com.example.omdb.ui.router

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RouterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    // Global
    private val TAG = RouterViewModel::class.java.simpleName

    init {
        // TODO: something here
    }
}