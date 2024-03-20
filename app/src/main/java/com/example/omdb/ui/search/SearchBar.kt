package com.example.omdb.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.omdb.R

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onSearchQueryUpdated: (String) -> Unit,
    onClearSearchQuery: () -> Unit,
    onSearch: () -> Unit,
    onBottomMeasured: (Dp) -> Unit
) {
    val localDensity = LocalDensity.current
    val focusManager = LocalFocusManager.current
    ElevatedCard(
        modifier = modifier
            .onGloballyPositioned {
                onBottomMeasured(
                    with(localDensity) { it.size.height.toDp() + 16.dp }
                )
            },
        shape = MaterialTheme.shapes.small
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = query,
            onValueChange = onSearchQueryUpdated,
            placeholder = {
                Text(text = stringResource(id = R.string.hint_search))
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(id = R.string.cd_search)
                )
            },
            trailingIcon = if (query.isNotEmpty()) {
                {
                    Icon(
                        modifier = Modifier
                            .clickable { onClearSearchQuery() },
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = stringResource(id = R.string.cd_cancel)
                    )
                }
            } else {
                null
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions {
                onSearch()
                focusManager.clearFocus()
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}