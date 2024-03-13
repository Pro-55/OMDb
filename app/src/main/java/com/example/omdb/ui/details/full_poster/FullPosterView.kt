package com.example.omdb.ui.details.full_poster

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.omdb.R
import com.example.omdb.domain.model.DragOrientation
import com.example.omdb.domain.state.FullPosterScreenState
import com.example.omdb.theme.OMDbTheme
import com.example.omdb.util.PhoneLightPreview
import com.example.omdb.util.gesture_detectors.detectDragDismissGesture
import com.example.omdb.views.ProImage

@Composable
fun FullPosterView(
    state: FullPosterScreenState,
    onBack: () -> Unit
) {
    var ratio by remember { mutableFloatStateOf(value = 0.75F) }
    var xOffset by remember { mutableStateOf(Dp(0.0F)) }
    var yOffset by remember { mutableStateOf(Dp(0.0F)) }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ProImage(
            modifier = Modifier
                .offset(x = xOffset, y = yOffset)
                .fillMaxWidth()
                .aspectRatio(ratio = ratio)
                .detectDragDismissGesture(
                    key1 = Unit,
                    localDensity = LocalDensity.current,
                    localConfiguration = LocalConfiguration.current,
                    orientation = DragOrientation.VERTICAL,
                    onDrag = { x, y ->
                        xOffset = x
                        yOffset = y
                    },
                    onDismiss = onBack
                ),
            model = state.url,
            placeholder = painterResource(id = R.drawable.placeholder_poster),
            contentDescription = stringResource(id = R.string.cd_big_poster),
            contentScale = ContentScale.Crop,
            onSuccess = {
                val size = it.painter.intrinsicSize
                ratio = size.width / size.height
            }
        )
    }
}

@PhoneLightPreview
@Composable
fun FullPosterViewPreview() {
    OMDbTheme {
        FullPosterView(
            state = FullPosterScreenState(
                url = "https://m.media-amazon.com/images/M/MV5BOTY4YjI2N2MtYmFlMC00ZjcyLTg3YjEtMDQyM2ZjYzQ5YWFkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg"
            ),
            onBack = {}
        )
    }
}