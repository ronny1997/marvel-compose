package com.mpapps.marvelcompose.ui.views.charactersList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.ui.infrastructure.SIDE_EFFECTS_KEY
import com.mpapps.marvelcompose.ui.views.charactersList.composable.CharacterItem
import com.mpapps.marvelcompose.ui.views.charactersList.composable.SnackbarError
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
    onNavigationRequested: (CharactersListEffect.Navigation) -> Unit,
) {
    var visible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("Generic error") }
    Box {
        SnackbarError(message = errorMessage, visible = visible, onHide = { visible = false })
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
                    var colorCharacter by remember {
                        mutableStateOf(Color(character.color ?: 0))
                    }
                    CharacterItem(
                        id = character.id,
                        name = character.name,
                        thumbnailUrl = character.thumbnailUrl,
                        colorCharacter = colorCharacter,
                        chargeImage = character.bitmapThumbnail?.let { false } ?: true,
                        onAction = {
                            onEventSend(it as CharactersListEvent)
                        },
                        onLoadImage = { bitmap, color ->
                            character.color = color
                            colorCharacter = Color(character.color ?: 0)
                            character.bitmapThumbnail = bitmap
                        },
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
    }
    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is CharactersListEffect.Navigation.NavigateToDetail -> {
                    onNavigationRequested(effect)
                }

                is CharactersListEffect.ShowError -> {
                    errorMessage = effect.message
                    visible = true
                }
            }
        }?.collect()
    }
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