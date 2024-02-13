package com.mpapps.marvelcompose.ui.views.charactersList

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.animation.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mpapps.marvelcompose.ui.infrastructure.SIDE_EFFECTS_KEY
import com.mpapps.marvelcompose.ui.views.charactersList.model.CharactersUi
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
        GridComposeCharacters(
            list = state.data,
            loadDataCharacter = {
                LoadImageAndGetDominantColor(it) { newCharacterUi ->
                    onEventSend(CharactersListEvent.UpdatePainterImage(newCharacterUi))
                }
            },
            onClickCharacter = {
                onEventSend(CharactersListEvent.NavigationToDetail(it))
            },
            onLoadMore = {
                onEventSend(CharactersListEvent.GetCharacters)
            }
        )
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
fun GridComposeCharacters(
    list: List<CharactersUi>,
    loadDataCharacter: @Composable (CharactersUi) -> Unit,
    onClickCharacter: (CharactersUi) -> Unit,
    onLoadMore: () -> Unit,
) {
    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Adaptive(minSize = 128.dp),
    ) {
        items(list) { character ->
            CharacterItem(
                id = character.id,
                name = character.name,
                thumbnail = character.thumbnail,
                onClickCharacter = { onClickCharacter(character) },
                colorCharacter = character.color,
            ) {
                loadDataCharacter(character)
            }
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
                onLoadMore()
            }
        }.collect()
    }
}

@Composable
fun CharacterItem(
    id: String,
    name: String,
    thumbnail: String,
    onClickCharacter: (String) -> Unit,
    colorCharacter: ULong?,
    onLoadData: @Composable () -> Unit,
) {
    val dominantColor = remember { Animatable(Color.LightGray) }
    val painter = painterColor(thumbnail)
    if (colorCharacter == null) {
        onLoadData()
    }

    LaunchedEffect(colorCharacter) {
        colorCharacter?.let {
            dominantColor.animateTo(Color(it))
        }
    }
    val textColor = if (dominantColor.value.luminance() > 0.5f) Color.Black else Color.White
    Column(
        modifier = Modifier
            .padding(4.dp)
            .widthIn(200.dp)
            .heightIn(200.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = dominantColor.value.copy(alpha = 0.8f))
            .clickable(onClick = {
                onClickCharacter(id)
            }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painter,
                contentDescription = "Characters",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Inside,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name.uppercase(),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                color = textColor
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun LoadImageAndGetDominantColor(
    character: CharactersUi,
    onBackResult: (CharactersUi) -> Unit
) {
    val painter = painterColor(character.thumbnail)

    val imageState = painter.state
    LaunchedEffect(imageState) {
        if (imageState is AsyncImagePainter.State.Success) {
            val drawable: Drawable = imageState.result.drawable
            val bitmap = (drawable as? BitmapDrawable)?.bitmap

            bitmap?.let {
                val convertedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                val palette = Palette.from(convertedBitmap).generate()
                val dominantSwatch = palette.dominantSwatch

                val dominantColor = dominantSwatch?.rgb?.let {
                    Color(dominantSwatch.rgb)
                } ?: Color.LightGray
                onBackResult(character.copy(color = dominantColor.value))
            }
        }
    }
}

@Composable
fun painterColor(thumbnail: String): AsyncImagePainter {
    return rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(thumbnail)
            .size(coil.size.Size.ORIGINAL)
            .build()
    )
}

@Preview
@Composable
fun Preview() = GridComposeCharacters(
    list = listOf(
        CharactersUi(
            id = "",
            name = "",
            description = "",
            thumbnail = "",
        )
    ), loadDataCharacter = {}, onClickCharacter = {}, onLoadMore = {})