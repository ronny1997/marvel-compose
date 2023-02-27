package com.patric.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.patric.core.domain.error.CustomError
import com.patric.core.utils.ErrorApp
import com.patric.ui.theme.AppTheme

@Composable
fun ErrorBanner(
    errorApp: ErrorApp
) {
    val errorState by errorApp.viewState.collectAsState()
    val message = errorState?.message ?: ""

    val density = LocalDensity.current
    AnimatedVisibility(visible = message.isNotBlank(),
        enter = slideInVertically { with(density) { -40.dp.roundToPx() } }
                + expandVertically(expandFrom = Alignment.Top)
                + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.error)
                .zIndex(1f)
        ) {
            Box(
                modifier = Modifier.weight(5f), contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,
                        top = 16.dp
                    ),
                    text = message,
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )
            }
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            errorApp.showError(CustomError.None)
                        })
                    },
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
fun LoginErrorPreview() {
    val errorApp = ErrorApp()
    errorApp.showError(CustomError.Generic("Lorem Ipsum is simply dummy text of the printing and typesetting industry."))
    AppTheme {
        ErrorBanner(
            errorApp
        )
    }
}