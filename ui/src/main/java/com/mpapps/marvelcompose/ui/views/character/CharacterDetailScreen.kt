package com.mpapps.marvelcompose.ui.views.character

import android.graphics.Bitmap
import androidx.compose.animation.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.domain.model.Comic
import com.mpapps.marvelcompose.ui.R
import com.mpapps.marvelcompose.ui.infrastructure.SIDE_EFFECTS_KEY
import com.mpapps.marvelcompose.ui.views.character.composables.ComicItem
import com.mpapps.marvelcompose.ui.views.character.state.CharacterDetailEffect
import com.mpapps.marvelcompose.ui.views.character.state.CharacterDetailEvent
import com.mpapps.marvelcompose.ui.views.character.state.CharacterDetailViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

@Composable
fun CharacterDetailScreen(
    state: CharacterDetailViewState,
    effectFlow: Flow<CharacterDetailEffect>?,
    onEventSend: (CharacterDetailEvent) -> Unit,
    onNavigationRequested: (CharacterDetailEffect.Navigation) -> Unit,
) {
    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->

        }
    }

    val dominantColor = remember { Animatable(Color.Transparent) }

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
        HeaderComic(
            state.character?.name,
            state.character?.bitmapThumbnail,
            dominantColor.value.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Description(state.character?.description ?: "")
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "COMICS",
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = textColor
            )
        )
        Spacer(modifier = Modifier.height(15.dp))
        ComicList(state.comicList)
    }
}

@Composable
fun ComicList(items: List<Comic>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(items.size) { index ->

            ComicItem(items[index].title, items[index].thumbnail)

        }
    }
}

@Composable
fun HeaderComic(
    name: String?,
    bitmapThumbnail: Bitmap?,
    background: Color,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 5.dp
    ) {
        Box(
            modifier = Modifier
                .height(250.dp)
                .width(200.dp)
                .background(color = background),
            contentAlignment = Alignment.TopCenter
        ) {
            bitmapThumbnail?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .height(200.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
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
                    .padding(10.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = name?.uppercase() ?: "",
                    color = Color.White,
                )
            }
        }
    }
}

@Composable
fun Description(description: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(5.dp))
            .background(Color.White.copy(alpha = 0.5f))
            .padding(5.dp)
            .fillMaxWidth()
    ) {
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
}

@Preview
@Composable
fun CharacterDetailScreenPreview() = CharacterDetailScreen(
    state = CharacterDetailViewState(
        character = Characters(
            "id",
            "name",
            description = "",
            bitmapThumbnail = null,
            thumbnailUrl = ""
        )
    ),
    null,
    {},
    {}
)