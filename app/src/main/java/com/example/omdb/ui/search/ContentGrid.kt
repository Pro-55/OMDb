package com.example.omdb.ui.search

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.omdb.R
import com.example.omdb.domain.model.SearchResult
import com.example.omdb.domain.model.ShortContent
import com.example.omdb.util.extensions.detectHoldGestures
import com.example.omdb.views.ProImage

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
    val focusManager = LocalFocusManager.current
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        state = state,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        item(span = { GridItemSpan(currentLineSpan = 2) }) {
            Spacer(
                modifier = Modifier
                    .height(height = searchBottom)
            )
        }
        items(items = result.search) { content ->
            Surface(
                modifier = Modifier
                    .aspectRatio(ratio = 0.75F)
                    .padding(all = 8.dp)
                    .detectHoldGestures(
                        key1 = Unit,
                        interactionSource = interactionSource,
                        onClick = {
                            focusManager.clearFocus(force = true)
                            onContentClicked(content)
                        },
                        onHold = {
                            focusManager.clearFocus(force = true)
                            onHold(content)
                        },
                        onRelease = onRelease
                    ),
                shape = RoundedCornerShape(size = 8.dp)
            ) {
                ProImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    model = content.poster,
                    placeholder = painterResource(id = R.drawable.placeholder_poster),
                    contentDescription = stringResource(id = R.string.cd_poster),
                    contentScale = ContentScale.Crop
                )
            }
        }
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