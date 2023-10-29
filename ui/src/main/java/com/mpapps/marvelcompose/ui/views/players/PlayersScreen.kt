package com.mpapps.marvelcompose.ui.views.players

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mpapps.marvelcompose.ui.views.players.viewmodel.CharactersViewModel

@Composable
fun PlayersScreen() {
    val viewModel: CharactersViewModel = viewModel()
    Text(text = "PlayersScreen")
}