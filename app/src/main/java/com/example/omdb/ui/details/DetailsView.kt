package com.example.omdb.ui.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.omdb.theme.OMDbTheme
import com.example.omdb.util.PhoneLightPreview

@Composable
fun DetailsView() {
    Text(
        modifier = Modifier
            .fillMaxSize(),
        text = "Details!"
    )
}

@PhoneLightPreview
@Composable
fun DetailsViewPreview() {
    OMDbTheme {
        DetailsView()
    }
}