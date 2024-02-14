package com.example.omdb.ui.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdb.domain.model.Content
import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.model.ShortContent
import com.example.omdb.domain.state.DetailsScreenState
import com.example.omdb.domain.use_case.GetDetailsUseCase
import com.example.omdb.util.Constants
import com.example.omdb.util.extensions.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDetailsUseCase: GetDetailsUseCase
) : ViewModel() {

    // Global
    private val TAG = DetailsViewModel::class.java.simpleName
    private val _content = MutableLiveData<Resource<Content>>() // *
    val content: LiveData<Resource<Content>> = _content // *
    private var stateValue = DetailsScreenState()
    private val _state = MutableLiveData(stateValue)
    val state: LiveData<DetailsScreenState> = _state
    var isLoading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    init {
        val _id = savedStateHandle.get<String?>("contentId")
        val shortContent = savedStateHandle.get<String?>("shortContent")
            ?.toObject<ShortContent>()

        if (_id.isNullOrEmpty() && shortContent == null) {
            error = Constants.ERROR_MESSAGE_INVALID_REQUEST
        } else {
            stateValue = stateValue.copy(
                _id = _id,
                shortContent = shortContent
            )
            _state.value = stateValue
            getDetails(id = _id ?: shortContent?._id!!)
        }
    }

    fun getDetails(
        id: String,
        plot: String = "short"
    ) {
        getDetailsUseCase(
            id = id,
            plot = plot
        )
            .onEach {
                when (it) {
                    is Resource.Loading -> isLoading = true
                    is Resource.Success -> {
                        isLoading = false
                        stateValue = stateValue.copy(
                            content = it.data
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