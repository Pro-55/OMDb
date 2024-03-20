package com.example.omdb.ui.search

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.omdb.domain.model.SearchResult
import com.example.omdb.domain.model.ShortContent
import com.example.omdb.util.gesture_detectors.detectHoldGesture
import com.example.omdb.views.PosterView

@Composable
fun ContentGrid(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    result: SearchResult,
    searchBottom: Dp,
    onLoadMore: () -> Unit,
    onHold: (ShortContent) -> Unit,
    onRelease: () -> Unit,
    onContentClicked: (ShortContent) -> Unit
) {
    val state = rememberLazyGridState()
    val interactionSource = remember { MutableInteractionSource() }
    var tappedContent by remember { mutableStateOf<ShortContent?>(null) }
    val focusManager = LocalFocusManager.current
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        state = state,
        contentPadding = PaddingValues(
            horizontal = 4.dp,
            vertical = 8.dp
        )
    ) {
        item(span = { GridItemSpan(currentLineSpan = 2) }) {
            Spacer(
                modifier = Modifier
                    .height(height = searchBottom)
            )
        }
        items(items = result.search) { content ->
            PosterView(
                modifier = Modifier
                    .padding(all = 4.dp)
                    .detectHoldGesture(
                        key1 = Unit,
                        interactionSource = interactionSource,
                        onClick = {
                            focusManager.clearFocus()
                            onContentClicked(content)
                        },
                        onHold = {
                            focusManager.clearFocus()
                            onHold(content)
                        },
                        onRelease = onRelease,
                        onTapStateChange = { isTapped ->
                            tappedContent = if (isTapped) content else null
                        }
                    )
                    .scale(
                        scale = if (content == tappedContent) {
                            0.95F
                        } else {
                            1.0F
                        }
                    ),
                poster = content.poster
            )
        }
    }
    LaunchedEffect(key1 = state.isScrollInProgress) {
        if (!state.isScrollInProgress) return@LaunchedEffect
        focusManager.clearFocus()
        tappedContent = null
    }
    LaunchedEffect(key1 = state.canScrollForward) {
        if (!isLoading
            && !state.canScrollForward
            && result.total > result.search.size
        ) {
            onLoadMore()
        }
    }
}