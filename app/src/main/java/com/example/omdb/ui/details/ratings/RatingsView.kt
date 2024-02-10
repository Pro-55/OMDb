package com.example.omdb.ui.details.ratings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.omdb.theme.OMDbTheme
import com.example.omdb.util.PhoneLightPreview

@Composable
fun RatingsView() {
    Text(
        modifier = Modifier
            .fillMaxSize(),
        text = "Ratings!"
    )
}

@PhoneLightPreview
@Composable
fun RatingsViewPreview() {
    OMDbTheme {
        RatingsView()
    }
}