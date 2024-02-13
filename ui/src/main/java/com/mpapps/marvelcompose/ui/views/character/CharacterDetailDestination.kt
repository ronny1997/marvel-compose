package com.mpapps.marvelcompose.ui.views.character

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mpapps.marvelcompose.ui.views.character.state.CharacterDetailEvent
import com.mpapps.marvelcompose.ui.views.character.viewmodel.CharacterDetailViewModel

@Composable
fun CharacterDetailDestination(navController: NavController, encodeJsonUri: String) {
    val vm: CharacterDetailViewModel = hiltViewModel()
    LaunchedEffect(key1 = true, block = {
        vm.setEvent(CharacterDetailEvent.InitData(encodeJsonUri))
    })
    CharacterDetailScreen(
        state = vm.uiState,
        effectFlow = vm.effect,
        onEventSend = { event ->
            vm.setEvent(event)
        },
        onNavigationRequested = {

        }
    )

}