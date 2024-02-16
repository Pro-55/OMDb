package com.example.omdb.ui.authentication

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.omdb.R
import com.example.omdb.domain.state.SignUpScreenState
import com.example.omdb.domain.state.TextFieldState
import com.example.omdb.theme.OMDbTheme
import com.example.omdb.util.PhoneLightPreview
import com.example.omdb.views.ProImage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpView(
    snackbarHostState: SnackbarHostState,
    isLoading: Boolean,
    state: SignUpScreenState,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSignUpWithGoogle: () -> Unit,
    onSignUpWithFacebook: () -> Unit,
    onSave: () -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .height(height = 80.dp)
            )
            ProImage(
                modifier = Modifier
                    .size(size = 120.dp)
                    .clip(shape = CircleShape),
                model = state.profileUrl,
                placeholder = painterResource(id = R.drawable.ic_profile_placeholder),
                contentDescription = stringResource(id = R.string.cd_profile)
            )
            Spacer(
                modifier = Modifier
                    .height(height = 16.dp)
            )
            PersonalDetailsView(
                isLoading = isLoading,
                firstName = state.firstName,
                onFirstNameChange = onFirstNameChange,
                lastName = state.lastName,
                onLastNameChange = onLastNameChange,
                email = state.email,
                onEmailChange = onEmailChange
            )
            Spacer(
                modifier = Modifier
                    .height(height = 16.dp)
            )
            ORSeparatorView()
            Spacer(
                modifier = Modifier
                    .height(height = 16.dp)
            )
            SingleSignOnOptionView(
                isLoading = isLoading,
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = stringResource(id = R.string.cd_sign_up_with_google),
                text = stringResource(id = R.string.btn_sign_up_with_google),
                onClick = onSignUpWithGoogle
            )
            Spacer(
                modifier = Modifier
                    .height(height = 16.dp)
            )
            SingleSignOnOptionView(
                isLoading = isLoading,
                painter = painterResource(id = R.drawable.ic_facebook),
                contentDescription = stringResource(id = R.string.cd_sign_up_with_facebook),
                text = stringResource(id = R.string.btn_sign_up_with_facebook),
                onClick = onSignUpWithFacebook
            )
            Spacer(
                modifier = Modifier
                    .defaultMinSize(minHeight = 16.dp)
                    .weight(weight = 1.0F)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(size = 8.dp),
                enabled = !isLoading,
                onClick = onSave
            ) {
                Text(
                    text = stringResource(id = R.string.btn_save),
                    style = TextStyle(
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
            Spacer(
                modifier = Modifier
                    .height(height = 16.dp)
            )
        }
    }
}

@PhoneLightPreview
@Composable
fun SignUpViewPreview() {
    OMDbTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        SignUpView(
            snackbarHostState = snackbarHostState,
            isLoading = false,
            state = SignUpScreenState(
                firstName = TextFieldState(text = "Pranit"),
                lastName = TextFieldState(text = "Rane"),
                email = TextFieldState(text = "pranitrane619@gmail.com")
            ),
            onFirstNameChange = {},
            onLastNameChange = {},
            onEmailChange = {},
            onSignUpWithGoogle = {},
            onSignUpWithFacebook = {},
            onSave = {}
        )
    }
}