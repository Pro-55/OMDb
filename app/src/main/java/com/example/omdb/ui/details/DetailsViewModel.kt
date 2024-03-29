package com.example.omdb.ui.details

import android.content.Context
import android.content.Intent
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
import com.example.omdb.framework.navigation.DeepLinks
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
    private var stateValue = DetailsScreenState()
    private val _state = MutableLiveData(stateValue)
    val state: LiveData<DetailsScreenState> = _state
    var isLoading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    init {
        val id = savedStateHandle.get<String?>("contentId")
        val shortContent = savedStateHandle.get<String?>("shortContent")
            ?.toObject<ShortContent>()

        if (id.isNullOrEmpty() && shortContent == null) {
            error = Constants.ERROR_MESSAGE_INVALID_REQUEST
        } else {
            stateValue = stateValue.copy(
                _id = id,
                shortContent = shortContent
            )
            _state.value = stateValue
            getDetails(id = id ?: shortContent?._id!!)
        }
    }

    private fun getDetails(
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

    fun share(
        context: Context,
        content: Content
    ) {
        val url = DeepLinks.getDetailsDeepLink(contentId = content._id)
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TITLE, content.title)
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/*"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }
}