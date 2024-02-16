package com.example.omdb.ui.router

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.use_case.GetSignUpStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RouterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSignUpStatusUseCase: GetSignUpStatusUseCase
) : ViewModel() {

    // Global
    private val TAG = RouterViewModel::class.java.simpleName
    var loginStatus by mutableStateOf<Boolean?>(null)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    init {
        getLoginStatus()
    }

    private fun getLoginStatus() {
        getSignUpStatusUseCase()
            .onEach {
                when (it) {
                    is Resource.Loading -> Unit
                    is Resource.Success -> loginStatus = it.data
                    is Resource.Error -> error = it.msg
                }
            }
            .launchIn(viewModelScope)
    }
}