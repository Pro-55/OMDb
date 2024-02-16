package com.example.omdb.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.omdb.R
import com.example.omdb.domain.state.HomeScreenState
import com.example.omdb.theme.OMDbTheme
import com.example.omdb.util.PhoneLightPreview
import com.example.omdb.views.ProImage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(
    snackbarHostState: SnackbarHostState,
    state: HomeScreenState,
    onProfileClicked: () -> Unit,
    onMovieSelected: () -> Unit,
    onSeriesSelected: () -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    modifier = Modifier
                        .weight(weight = 1.0F),
                    text = state.greeting,
                    maxLines = 1,
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(
                    modifier = Modifier
                        .width(width = 16.dp)
                )
                ProImage(
                    modifier = Modifier
                        .size(size = 44.dp)
                        .clip(shape = CircleShape)
                        .clickable { onProfileClicked() },
                    model = state.user?.profileUrl,
                    placeholder = painterResource(id = R.drawable.ic_profile_placeholder),
                    contentDescription = stringResource(id = R.string.cd_profile)
                )
            }
            Spacer(
                modifier = Modifier
                    .height(height = 16.dp)
            )
            ContentTypeCard(
                modifier = Modifier
                    .weight(weight = 1.0F),
                icon = painterResource(id = R.drawable.ic_movies),
                label = stringResource(id = R.string.label_movies),
                contentDescription = stringResource(id = R.string.cd_movies),
                onClick = onMovieSelected
            )
            Spacer(
                modifier = Modifier
                    .height(height = 16.dp)
            )
            ContentTypeCard(
                modifier = Modifier
                    .weight(weight = 1.0F),
                icon = painterResource(id = R.drawable.ic_series),
                label = stringResource(id = R.string.label_series),
                contentDescription = stringResource(id = R.string.cd_series),
                onClick = onSeriesSelected
            )
        }
    }
}

@PhoneLightPreview
@Composable
fun HomeViewPreview() {
    OMDbTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        HomeView(
            snackbarHostState = snackbarHostState,
            state = HomeScreenState(
                greeting = "Hello there!"
            ),
            onProfileClicked = {},
            onMovieSelected = {},
            onSeriesSelected = {}
        )
    }
}