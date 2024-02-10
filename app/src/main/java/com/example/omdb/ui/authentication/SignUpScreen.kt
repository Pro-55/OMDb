package com.example.omdb.ui.authentication

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    SignUpView()
}