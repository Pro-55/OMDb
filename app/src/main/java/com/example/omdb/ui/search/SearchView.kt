package com.example.omdb.ui.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.omdb.theme.OMDbTheme
import com.example.omdb.util.PhoneLightPreview

@Composable
fun SearchView() {
    Text(
        modifier = Modifier
            .fillMaxSize(),
        text = "Search!"
    )
}

@PhoneLightPreview
@Composable
fun SearchViewPreview() {
    OMDbTheme {
        SearchView()
    }
}