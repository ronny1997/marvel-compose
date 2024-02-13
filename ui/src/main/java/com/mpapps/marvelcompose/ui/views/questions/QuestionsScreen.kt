package com.mpapps.marvelcompose.ui.views.questions

import android.net.Uri
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.gson.Gson
import com.mpapps.marvelcompose.domain.model.Comic
import com.mpapps.marvelcompose.ui.R
import com.mpapps.marvelcompose.ui.views.players.model.CharactersUi
import com.mpapps.marvelcompose.ui.views.questions.state.CharacterDetailEvent
import com.mpapps.marvelcompose.ui.views.questions.viewmodel.CharactersDetailViewModel

@Composable
fun QuestionsScreen(jsonCharacter: String?) {
    val decodeJson = Uri.decode(jsonCharacter)
    val characters = Gson().fromJson(decodeJson, CharactersUi::class.java)
    val dominantColor = remember { Animatable(Color.Transparent) }
    val viewModel: CharactersDetailViewModel = hiltViewModel()
    val uiState = viewModel.uiState

    LaunchedEffect(uiState.specificState) {
        if (uiState.specificState?.data?.isEmpty() != false) {
            viewModel.onEvent(CharacterDetailEvent.GetComics(characters.id))
        }
    }

    LaunchedEffect(characters.color) {
        characters.color?.let {
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
        Image(
            painter = PainterColor(thumbnail = characters.thumbnail),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = characters.name.uppercase(),
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
            Description(characters.description)
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
            ComicList(uiState.specificState?.data ?: listOf())
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
            ) {
                Image(
                    painter = PainterColor(thumbnail = items[index].thumbnail),
                    contentDescription = "Comic photo",
                    modifier = Modifier
                        .height(200.dp)
                )
                Text(text = items[index].title)
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

@Composable
fun PainterColor(thumbnail: String): AsyncImagePainter {
    return rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(thumbnail)
            .size(Size.ORIGINAL)
            .build()
    )
}