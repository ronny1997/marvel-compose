package com.mpapps.marvelcompose.ui.views.charactersList

import android.graphics.Bitmap
import androidx.compose.animation.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.ui.infrastructure.SIDE_EFFECTS_KEY
import com.mpapps.marvelcompose.ui.views.charactersList.state.CharactersListEffect
import com.mpapps.marvelcompose.ui.views.charactersList.state.CharactersListEvent
import com.mpapps.marvelcompose.ui.views.charactersList.state.CharactersListViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach

@Composable
fun CharactersListScreen(
    state: CharactersListViewState,
    effectFlow: Flow<CharactersListEffect>?,
    onEventSend: (CharactersListEvent) -> Unit,
    onNavigationRequested: (CharactersListEffect.Navigation) -> Unit
) {
    Column {
        if (state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }
        val lazyGridState = rememberLazyGridState()
        LazyVerticalGrid(
            state = lazyGridState,
            columns = GridCells.Adaptive(minSize = 100.dp),
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(
                items = state.data.values.toList(),
            ) { character ->
                CharacterItem(
                    name = character.name,
                    thumbnailUrl = character.bitmapThumbnail,
                    colorCharacter = Color(character.color ?: 0),
                    onClickCharacter = {
                        onEventSend(CharactersListEvent.NavigationToDetail(character))
                    }
                )
            }
        }
        LaunchedEffect(lazyGridState) {
            val snapshotFlowItems = snapshotFlow {
                lazyGridState.layoutInfo.totalItemsCount
            }
            val snapshotFlowIndex = snapshotFlow {
                lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            }
            snapshotFlowItems.combine(snapshotFlowIndex) { totalItems, lastIndex ->
                if (totalItems > 0 && lastIndex >= totalItems - 1) {
                    onEventSend(CharactersListEvent.GetCharacters)
                }
            }.collect()
        }
    }
    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is CharactersListEffect.Navigation.NavigateToDetail -> {
                    onNavigationRequested(effect)
                }
            }
        }?.collect()
    }
}

@Composable
fun CharacterItem(
    name: String,
    thumbnailUrl: Bitmap?,
    colorCharacter: Color?,
    onClickCharacter: () -> Unit
) {
    val dominantColor = remember { Animatable(Color.LightGray) }
    LaunchedEffect(colorCharacter) {
        colorCharacter?.let {
            dominantColor.animateTo(it)
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClickCharacter()
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 5.dp
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(100.dp)
                .background(color = dominantColor.value)
        ) {
            thumbnailUrl?.let {
                Image(
                    bitmap = thumbnailUrl.asImageBitmap(),
                    contentDescription = "",
                    contentScale = ContentScale.Inside
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 300f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(text = name, color = Color.White)
            }
        }
    }
}

@Preview
@Composable
fun TestComposable() {
    CharacterItem(name = "Test", thumbnailUrl = null, null, {})
}

@Preview
@Composable
fun Preview() = CharactersListScreen(
    state = CharactersListViewState(
        data = mutableMapOf(
            Pair(
                "", Characters(
                    "id",
                    "name",
                    description = "",
                    bitmapThumbnail = null,
                    thumbnailUrl = ""
                )
            ),
            Pair(
                "", Characters(
                    "id",
                    "name",
                    description = "",
                    bitmapThumbnail = null,
                    thumbnailUrl = ""
                )
            ),
            Pair(
                "", Characters(
                    "id",
                    "name",
                    description = "",
                    bitmapThumbnail = null,
                    thumbnailUrl = ""
                )
            ),
        ),
    ),
    effectFlow = null,
    {},
    {}
)