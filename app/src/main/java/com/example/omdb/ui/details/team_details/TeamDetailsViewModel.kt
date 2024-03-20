package com.example.omdb.ui.details.team_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.omdb.domain.model.TeamDetails
import com.example.omdb.domain.state.TeamDetailsScreenState
import com.example.omdb.util.extensions.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TeamDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Global
    private val TAG = TeamDetailsViewModel::class.java.simpleName
    private var stateValue = TeamDetailsScreenState()
    private val _state = MutableLiveData(stateValue)
    val state: LiveData<TeamDetailsScreenState> = _state

    init {
        val teamDetails = savedStateHandle.get<String?>("team")
            ?.toObject<TeamDetails>()

        stateValue = stateValue.copy(team = teamDetails)
        _state.value = stateValue
    }
}