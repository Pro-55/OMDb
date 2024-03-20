package com.example.omdb.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.omdb.R

@Composable
fun ActionBar(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String? = null,
    onBack: (() -> Unit)? = null,
    actionContent: (@Composable () -> Unit)? = null
) {
    Surface(
        modifier = modifier
            .height(height = 56.dp),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onBack != null) {
                Icon(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(
                            ratio = 1.0F,
                            matchHeightConstraintsFirst = true
                        )
                        .clickable { onBack() }
                        .padding(all = 16.dp),
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(id = R.string.cd_back_button)
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .width(width = 16.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weight = 1.0F),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                subTitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            if (actionContent != null) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentWidth()
                ) {
                    actionContent()
                }
            } else {
                Spacer(
                    modifier = Modifier
                        .width(width = 16.dp)
                )
            }
        }
    }
}