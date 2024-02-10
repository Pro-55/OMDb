package com.example.omdb.ui.details.episodes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.omdb.theme.OMDbTheme
import com.example.omdb.util.PhoneLightPreview

@Composable
fun EpisodesView() {
    Text(
        modifier = Modifier
            .fillMaxSize(),
        text = "Episodes!"
    )
}

@PhoneLightPreview
@Composable
fun EpisodesViewPreview() {
    OMDbTheme {
        EpisodesView()
    }
}