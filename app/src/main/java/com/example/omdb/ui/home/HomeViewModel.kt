package com.example.omdb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.model.User
import com.example.omdb.domain.state.HomeScreenState
import com.example.omdb.domain.use_case.GetCurrentUserUseCase
import com.example.omdb.domain.use_case.GetGreetingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getGreetingUseCase: GetGreetingUseCase
) : ViewModel() {

    // Global
    private val TAG = HomeViewModel::class.java.simpleName
    private val _user = MutableLiveData<Resource<User>>() // *
    val user: LiveData<Resource<User>> = _user // *
    private var stateValue = HomeScreenState()
    private val _state = MutableLiveData(stateValue)
    val state: LiveData<HomeScreenState> = _state

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        getCurrentUserUseCase()
            .onEach {
                when (it) {
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        stateValue = stateValue.copy(user = it.data)
                        _state.value = stateValue
                        getGreeting()
                    }
                    is Resource.Error -> {
                        stateValue = stateValue.copy(user = null)
                        _state.value = stateValue
                        getGreeting()
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getGreeting() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                while (true) {
                    val greeting = runCatching {
                        getGreetingUseCase(userName = stateValue.user?.firstName)
                    }
                        .getOrNull()
                    if (!greeting.isNullOrEmpty()) {
                        withContext(Dispatchers.Main) {
                            stateValue = stateValue.copy(greeting = greeting)
                            _state.value = stateValue
                        }
                    }
                    delay(30000L)
                }
            }
        }
    }
}