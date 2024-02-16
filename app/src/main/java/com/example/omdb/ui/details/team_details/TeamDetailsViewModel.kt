package com.example.omdb.ui.details.team_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.omdb.domain.model.TeamDetails
import com.example.omdb.domain.state.TeamDetailsScreenState
import com.example.omdb.util.Constants
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
    var error by mutableStateOf<String?>(null)
        private set

    init {
        val teamDetails = savedStateHandle.get<String?>("team")
            ?.toObject<TeamDetails>()

        if (teamDetails == null) {
            error = Constants.ERROR_MESSAGE_INVALID_REQUEST
        } else {
            stateValue = stateValue.copy(team = teamDetails)
            _state.value = stateValue
        }
    }
}