package com.mpapps.marvelcompose.ui.views.players

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mpapps.marvelcompose.ui.views.players.viewmodel.CharactersViewModel

@Composable
fun PlayersScreen() {
    val viewModel: CharactersViewModel = hiltViewModel()

    viewModel.getCharacters()
    Text(text = "PlayersScreen")
}