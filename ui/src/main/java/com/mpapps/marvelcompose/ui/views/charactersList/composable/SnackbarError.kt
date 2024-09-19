package com.mpapps.marvelcompose.ui.views.charactersList.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mpapps.marvelcompose.ui.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SnackbarError(
    message: String?,
    visible: Boolean,
    onHide: () -> Unit = {},
) {
    val density = LocalDensity.current
    LaunchedEffect(key1 = true) {
        launch {
            delay(5000)
            onHide()
        }
    }
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 1000)
        ) {
            with(density) { -400.dp.roundToPx() }
        } + fadeIn(
            animationSpec = tween(durationMillis = 1000),
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically(animationSpec = tween(durationMillis = 1000)) + fadeOut(
            animationSpec = tween(durationMillis = 1000)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box (
                modifier = Modifier.padding(10.dp)
            ){
                Text(text = message ?: "Generick error")
            }
            Icon(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.spiderman),
                contentDescription = "error", tint = Color.Unspecified
            )

        }
    }
}


@Preview
@Composable
fun SnackbarErrorPreview() = SnackbarError(message = "GENERICK ERROR", visible = true)