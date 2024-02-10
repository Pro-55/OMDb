package com.example.omdb.ui.details.full_poster

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.omdb.theme.OMDbTheme
import com.example.omdb.util.PhoneLightPreview

@Composable
fun FullPosterView() {
    Text(
        modifier = Modifier
            .fillMaxSize(),
        text = "Full Poster!"
    )
}

@PhoneLightPreview
@Composable
fun FullPosterViewPreview() {
    OMDbTheme {
        FullPosterView()
    }
}