package com.example.omdb.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.model.User
import com.example.omdb.domain.use_case.FetchFirebaseUserUseCase
import com.example.omdb.domain.use_case.SignUpUseCase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val fetchFirebaseUserUseCase: FetchFirebaseUserUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    // Global
    private val TAG = SignUpViewModel::class.java.simpleName
    private val _firebaseUser = MutableLiveData<Resource<FirebaseUser>>()
    val firebaseUser: LiveData<Resource<FirebaseUser>> = _firebaseUser
    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>> = _user

    fun fetchFirebaseUser(credential: AuthCredential) {
        fetchFirebaseUserUseCase(credential = credential)
            .onEach { _firebaseUser.postValue(it) }
            .launchIn(viewModelScope)
    }

    fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        profileUrl: String?
    ) {
        signUpUseCase(
            firstName = firstName,
            lastName = lastName,
            email = email,
            profileUrl = profileUrl
        )
            .onEach { _user.postValue(it) }
            .launchIn(viewModelScope)
    }
}