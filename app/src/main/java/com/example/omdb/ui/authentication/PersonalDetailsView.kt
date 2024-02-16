package com.example.omdb.ui.authentication

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.omdb.R
import com.example.omdb.domain.state.TextFieldState

@Composable
fun PersonalDetailsView(
    isLoading: Boolean,
    firstName: TextFieldState,
    onFirstNameChange: (String) -> Unit,
    lastName: TextFieldState,
    onLastNameChange: (String) -> Unit,
    email: TextFieldState,
    onEmailChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .weight(weight = 1.0F),
            label = {
                Text(text = stringResource(id = R.string.hint_first_name))
            },
            value = firstName.text,
            onValueChange = onFirstNameChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            isError = firstName.error != null,
            enabled = !isLoading
        )
        Spacer(
            modifier = Modifier
                .width(width = 8.dp)
        )
        TextField(
            modifier = Modifier
                .weight(weight = 1.0F),
            label = {
                Text(text = stringResource(id = R.string.hint_last_name))
            },
            value = lastName.text,
            onValueChange = onLastNameChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            isError = lastName.error != null,
            enabled = !isLoading
        )
    }
    Spacer(
        modifier = Modifier
            .height(height = 16.dp)
    )
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        label = {
            Text(text = stringResource(id = R.string.hint_email))
        },
        value = email.text,
        onValueChange = onEmailChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Email
        ),
        isError = email.error != null,
        enabled = !isLoading
    )
}