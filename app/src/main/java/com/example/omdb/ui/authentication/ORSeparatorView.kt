package com.example.omdb.ui.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.omdb.R

@Composable
fun ORSeparatorView() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(weight = 1.0F)
                .height(height = 1.dp)
                .background(color = MaterialTheme.colorScheme.primary)
        )
        Spacer(
            modifier = Modifier
                .width(width = 8.dp)
        )
        Text(
            text = stringResource(id = R.string.label_or),
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(
            modifier = Modifier
                .width(width = 8.dp)
        )
        Box(
            modifier = Modifier
                .weight(weight = 1.0F)
                .height(height = 1.dp)
                .background(color = MaterialTheme.colorScheme.primary)
        )
    }
}