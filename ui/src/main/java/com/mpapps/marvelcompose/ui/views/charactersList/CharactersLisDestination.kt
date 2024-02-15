package com.mpapps.marvelcompose.ui.views.charactersList

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mpapps.marvelcompose.ui.views.charactersList.state.CharactersListEffect
import com.mpapps.marvelcompose.ui.views.charactersList.viewmodel.CharactersListViewModel
import com.mpapps.marvelcompose.ui.views.character.CharacterDetailRoute

@Composable
fun CharactersListDestinations(navController: NavController) {
    val vm: CharactersListViewModel = hiltViewModel()
    CharactersListScreen(
        state = vm.uiState,
        effectFlow = vm.effect,
        onEventSend = { event ->
            vm.setEvent(event)
        },
        onNavigationRequested = { effect ->
            when (effect) {
                is CharactersListEffect.Navigation.NavigateToDetail -> {
                    navController.navigate(
                        CharacterDetailRoute.getUriRouteDataWhitArguments(effect.charactersUi)
                    )
                }
            }
        }
    )

}