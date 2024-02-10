package com.example.omdb.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.omdb.theme.OMDbTheme
import com.example.omdb.util.PhoneLightPreview

@Composable
fun HomeView() {
    Text(
        modifier = Modifier
            .fillMaxSize(),
        text = "Home!"
    )
}

@PhoneLightPreview
@Composable
fun HomeViewPreview() {
    OMDbTheme {
        HomeView()
    }
}