package com.example.omdb.ui.authentication

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.omdb.theme.OMDbTheme
import com.example.omdb.util.PhoneLightPreview

@Composable
fun SignUpView() {
    Text(
        modifier = Modifier
            .fillMaxSize(),
        text = "Sign Up!"
    )
}

@PhoneLightPreview
@Composable
fun SignUpViewPreview() {
    OMDbTheme {
        SignUpView()
    }
}