package com.example.omdb.ui.details.episodes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.state.EpisodesScreenState
import com.example.omdb.domain.use_case.GetEpisodesUseCase
import com.example.omdb.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getEpisodesUseCase: GetEpisodesUseCase
) : ViewModel() {

    // Global
    private val TAG = EpisodesViewModel::class.java.simpleName
    private var stateValue = EpisodesScreenState()
    private val _state = MutableLiveData(stateValue)
    val state: LiveData<EpisodesScreenState> = _state
    var isLoading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    init {
        val id = savedStateHandle.get<String?>("contentId")
        val season = savedStateHandle.get<Int?>("season")

        if (id.isNullOrEmpty() || season == null) {
            error = Constants.ERROR_MESSAGE_INVALID_REQUEST
        } else {
            stateValue = stateValue.copy(
                _id = id,
                season = season
            )
            _state.value = stateValue
            getEpisodes(
                id = id,
                season = season
            )
        }
    }

    private fun getEpisodes(
        id: String,
        season: Int
    ) {
        getEpisodesUseCase(
            id = id,
            season = season
        )
            .onEach {
                when (it) {
                    is Resource.Loading -> isLoading = true
                    is Resource.Success -> {
                        isLoading = false
                        stateValue = stateValue.copy(
                            episodes = it.data!!.episodes
                        )
                        _state.value = stateValue
                    }
                    is Resource.Error -> {
                        error = it.msg
                        isLoading = false
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}