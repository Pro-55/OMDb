package com.example.omdb.ui.authentication

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.omdb.domain.state.SignUpScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateSignUpToHome: () -> Unit
) {
    val state by viewModel.state.observeAsState(initial = SignUpScreenState())
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val googleAuthLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) return@rememberLauncherForActivityResult
        viewModel.handleGoogleResult(result.data)
    }
    SignUpView(
        snackbarHostState = snackbarHostState,
        isLoading = viewModel.isLoading,
        state = state,
        onFirstNameChange = viewModel::updateFirstName,
        onLastNameChange = viewModel::updateLastName,
        onEmailChange = viewModel::updateEmail,
        onSignUpWithGoogle = {
            scope.launch(Dispatchers.IO) {
                val sender = viewModel.hitGoogleSignIn()
                googleAuthLauncher.launch(
                    IntentSenderRequest.Builder(
                        sender ?: return@launch
                    )
                        .build()
                )
            }
        },
        onSignUpWithFacebook = {
            scope.launch(Dispatchers.IO) {
                viewModel.hitMetaSignIn(context = context)
            }
        },
        onSave = viewModel::signUp
    )
    LaunchedEffect(key1 = viewModel.hasSignUpSuccessfully) {
        if (!viewModel.hasSignUpSuccessfully) return@LaunchedEffect
        navigateSignUpToHome()
    }
    LaunchedEffect(key1 = viewModel.error) {
        val error = viewModel.error?.trim()
        if (error.isNullOrEmpty()) return@LaunchedEffect
        snackbarHostState.showSnackbar(message = error)
    }
}