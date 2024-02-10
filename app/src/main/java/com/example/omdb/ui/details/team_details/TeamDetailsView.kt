package com.example.omdb.ui.details.team_details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.omdb.theme.OMDbTheme
import com.example.omdb.util.PhoneLightPreview

@Composable
fun TeamDetailsView() {
    Text(
        modifier = Modifier
            .fillMaxSize(),
        text = "Team Details!"
    )
}

@PhoneLightPreview
@Composable
fun TeamDetailsViewPreview() {
    OMDbTheme {
        TeamDetailsView()
    }
}