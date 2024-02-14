package com.example.omdb.ui.details.ratings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.omdb.domain.model.Rating
import com.example.omdb.domain.state.RatingsScreenState
import com.example.omdb.util.Constants
import com.example.omdb.util.extensions.toObjectList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RatingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Global
    private val TAG = RatingsViewModel::class.java.simpleName
    private var stateValue = RatingsScreenState()
    private val _state = MutableLiveData(stateValue)
    val state: LiveData<RatingsScreenState> = _state
    var error by mutableStateOf<String?>(null)
        private set

    init {
        val ratings = savedStateHandle.get<String?>("ratings")
            ?.toObjectList<Rating>()

        if (ratings.isNullOrEmpty()) {
            error = Constants.ERROR_MESSAGE_INVALID_REQUEST
        } else {
            stateValue = stateValue.copy(ratings = ratings)
            _state.value = stateValue
        }
    }
}