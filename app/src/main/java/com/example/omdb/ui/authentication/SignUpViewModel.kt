package com.example.omdb.ui.authentication

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdb.data.firebase.sso.GoogleAuthHelper
import com.example.omdb.data.firebase.sso.MetaAuthHelper
import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.state.SignUpScreenState
import com.example.omdb.domain.state.TextFieldState
import com.example.omdb.domain.use_case.FetchFirebaseUserUseCase
import com.example.omdb.domain.use_case.SignUpUseCase
import com.example.omdb.util.extensions.isValidEmail
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val googleAuthHelper: GoogleAuthHelper,
    private val metaAuthHelper: MetaAuthHelper,
    private val fetchFirebaseUserUseCase: FetchFirebaseUserUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    // Global
    private val TAG = SignUpViewModel::class.java.simpleName
    private var stateValue = SignUpScreenState()
    private val _state = MutableLiveData(stateValue)
    val state: LiveData<SignUpScreenState> = _state
    var isLoading by mutableStateOf(false)
        private set
    var hasSignUpSuccessfully by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    fun updateFirstName(firstName: String) {
        stateValue = stateValue.copy(
            firstName = TextFieldState(
                text = firstName.filter { it.isLetter() })
        )
        _state.value = stateValue
    }

    fun updateLastName(lastName: String) {
        stateValue = stateValue.copy(
            lastName = TextFieldState(
                text = lastName.filter { it.isLetter() })
        )
        _state.value = stateValue
    }

    fun updateEmail(email: String) {
        stateValue = stateValue.copy(
            email = TextFieldState(text = email)
        )
        _state.value = stateValue
    }

    suspend fun hitGoogleSignIn(): IntentSender? {
        return googleAuthHelper.signIn()
    }

    fun handleGoogleResult(data: Intent?) {
        val credential = googleAuthHelper.getAuthCredential(data)
        fetchFirebaseUser(credential)
    }

    suspend fun hitMetaSignIn(context: Context) {
        val credential = metaAuthHelper.signIn(context = context)
        fetchFirebaseUser(credential)
    }

    fun signUp() {
        if (isInvalid()) return
        signUpUseCase(
            firstName = stateValue.firstName.text,
            lastName = stateValue.lastName.text,
            email = stateValue.email.text,
            profileUrl = stateValue.profileUrl
        )
            .onEach {
                when (it) {
                    is Resource.Loading -> isLoading = true
                    is Resource.Success -> {
                        isLoading = false
                        hasSignUpSuccessfully = true
                    }
                    is Resource.Error -> {
                        error = it.msg
                        isLoading = false
                        _state.value = stateValue
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchFirebaseUser(credential: AuthCredential?) {
        fetchFirebaseUserUseCase(credential = credential)
            .onEach {
                when (it) {
                    is Resource.Loading -> isLoading = true
                    is Resource.Success -> {
                        isLoading = false
                        val user = it.data!!
                        val names = user.displayName?.split(" ")
                        stateValue = stateValue.copy(
                            firstName = TextFieldState(names?.firstOrNull() ?: ""),
                            lastName = TextFieldState(text = names?.lastOrNull() ?: ""),
                            email = TextFieldState(text = user.email ?: ""),
                            profileUrl = user.photoUrl?.toString()
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

    private fun isInvalid(): Boolean {
        val firstName = stateValue.firstName.text.trim()
        val firstNameError = if (firstName.isEmpty()) "" else null

        val lastName = stateValue.lastName.text.trim()
        val lastNameError = if (lastName.isEmpty()) "" else null

        val email = stateValue.email.text.trim()
        val emailError = if (email.isEmpty() || !email.isValidEmail()) "" else null

        stateValue = stateValue.copy(
            firstName = stateValue.firstName.copy(error = firstNameError),
            lastName = stateValue.lastName.copy(error = lastNameError),
            email = stateValue.email.copy(error = emailError)
        )
        _state.value = stateValue
        return firstNameError != null
                || lastNameError != null
                || emailError != null
    }
}