package com.mpapps.marvelcompose.ui.views.players

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.mpapps.marvelcompose.ui.views.players.state.CharacterEvent
import com.mpapps.marvelcompose.ui.views.players.viewmodel.CharactersViewModel

@Composable
fun PlayersScreen() {
    val vm: CharactersViewModel = hiltViewModel()
    val uiState = vm.uiState

    Column {
        Text(text = uiState.specificState?.data?.getOrNull(0)?.name ?: "")
        Button(onClick = { vm.onEvent(CharacterEvent.GetCharacters) }) {
            Text(text = "GetData")
        }
        if (uiState.generalState.isLoading) {
            CircularProgressIndicator()
        }
    }
}