package com.mpapps.marvelcompose.ui.views.charactersList.composable

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mpapps.marvelcompose.ui.infrastructure.OnAction
import com.mpapps.marvelcompose.ui.views.charactersList.state.CharactersListEvent

@Composable
fun CharacterItem(
    id: String,
    name: String,
    thumbnailUrl: String?,
    colorCharacter: Color?,
    chargeImage: Boolean,
    onAction: OnAction,
    onLoadImage: (Bitmap, Int?) -> Unit,
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
                onAction(CharactersListEvent.NavigationToDetail(id))
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
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(thumbnailUrl)
                    .crossfade(true)
                    .listener { _, result ->
                        if (chargeImage) {
                            loadImage(result.drawable) { bitmap, color ->
                                onLoadImage(bitmap, color)
                            }
                        }
                    }
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Inside
            )

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
    CharacterItem(
        id = "id",
        name = "Test",
        thumbnailUrl = null,
        colorCharacter = null,
        chargeImage = false,
        onAction = {},
        onLoadImage = { _, _ -> })
}

private fun loadImage(
    drawable: Drawable,
    onLoadImage: (Bitmap, Int?) -> Unit,
) {
    val bitmap = (drawable as BitmapDrawable).bitmap
    val convertBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
    val color = loadColor(convertBitmap)
    onLoadImage(convertBitmap, color)
}

private fun loadColor(bitmapThumbnail: Bitmap): Int? {
    return bitmapThumbnail.let {
        val palette = Palette.from(bitmapThumbnail).generate()
        val dominantSwatch = palette.dominantSwatch
        dominantSwatch?.rgb
    }
}