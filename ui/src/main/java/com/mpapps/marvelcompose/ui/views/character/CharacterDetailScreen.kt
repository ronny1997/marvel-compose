package com.mpapps.marvelcompose.ui.views.character

import androidx.compose.animation.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.mpapps.marvelcompose.domain.model.Comic
import com.mpapps.marvelcompose.ui.R
import com.mpapps.marvelcompose.ui.infrastructure.SIDE_EFFECTS_KEY
import com.mpapps.marvelcompose.ui.views.character.state.CharacterDetailEffect
import com.mpapps.marvelcompose.ui.views.character.state.CharacterDetailEvent
import com.mpapps.marvelcompose.ui.views.character.state.CharacterDetailViewState
import com.mpapps.marvelcompose.ui.views.character.viewmodel.CharacterDetailViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

@Composable
fun CharacterDetailScreen(
    state: CharacterDetailViewState,
    effectFlow: Flow<CharacterDetailEffect>?,
    onEventSend: (CharacterDetailEvent) -> Unit,
    onNavigationRequested: (CharacterDetailEffect.Navigation) -> Unit
) {
    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->

        }
    }

    val dominantColor = remember { Animatable(Color.Transparent) }
    val viewModel: CharacterDetailViewModel = hiltViewModel()
    val uiState = viewModel.uiState

    LaunchedEffect(state.character?.color) {
        state.character?.color?.let {
            dominantColor.animateTo(Color(it))
        }
    }
    val textColor = if (dominantColor.value.luminance() > 0.5f) Color.Black else Color.White

    Column(
        modifier = Modifier
            .background(color = dominantColor.value.copy(alpha = 0.8f))
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Spacer(modifier = Modifier.height(5.dp))
        state.character?.bitmapThumbnail?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = state.character?.name?.uppercase() ?: "",
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = textColor
            )
        )
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .background(Color.White.copy(alpha = 0.5f))
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Description(state.character?.description ?: "")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .background(Color.White.copy(alpha = 0.5f))
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            ComicList(uiState.comicList)
        }
    }
}

@Composable
fun ComicList(items: List<Comic>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    ) {
        items(items.size) { index ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(100.dp)
                    .height(240.dp)
            ) {
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(items[index].thumbnail)
                        .size(Size.ORIGINAL)
                        .build()
                )

                Image(
                    painter = painter,
                    contentDescription = "Comic photo",
                    modifier = Modifier.height(200.dp)

                )
                Text(
                    text = items[index].title,
                    maxLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                )
            }
        }
    }
}

@Composable
fun Description(description: String) {
    if (description.isEmpty()) {
        Icon(
            painter = painterResource(id = R.drawable.not_found_ic),
            contentDescription = "Icon not found",
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Este personaje no tiene descripción",
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
        )
    } else {
        Text(
            text = description,
            textAlign = TextAlign.Center,
        )
    }

}