package com.example.omdb.ui.details.team_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TeamDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Global
    private val TAG = TeamDetailsViewModel::class.java.simpleName

    init {
        // TODO: something here
    }
}