package com.mpapps.marvelcompose.ui.views.questions

import android.net.Uri
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.gson.Gson
import com.mpapps.marvelcompose.ui.views.players.model.CharactersUi
import com.mpapps.marvelcompose.ui.views.questions.viewmodel.CharactersDetailViewModel

@Composable
fun QuestionsScreen(jsonCharacter: String?) {
    val decodeJson = Uri.decode(jsonCharacter)
    val characters = Gson().fromJson(decodeJson, CharactersUi::class.java)
    val dominantColor = remember { Animatable(Color.Transparent) }
    val viewModel: CharactersDetailViewModel = viewModel()

    LaunchedEffect(characters.color) {
        characters.color?.let {
            dominantColor.animateTo(Color(it))
        }
    }
    val textColor = if (dominantColor.value.luminance() > 0.5f) Color.Black else Color.White

    Column(
        modifier = Modifier
            .padding(4.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = dominantColor.value.copy(alpha = 0.8f)),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
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
        Text(text = characters.description,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
                .background(Color.White.copy(alpha = 0.5f)))
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