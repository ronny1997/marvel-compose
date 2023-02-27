package com.patric.ui.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.patric.ui.theme.DarkBlue

@Composable
fun TopBar() {
    TopAppBar(
        modifier = Modifier, title = {
            Text(text = "Test")
        },
        backgroundColor = DarkBlue
    )
}

@Preview
@Composable
fun PreviewTopBar() = TopBar()