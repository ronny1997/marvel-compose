package com.mpapps.marvelcompose.ui.views.charactersList.state

import com.mpapps.marvelcompose.ui.infrastructure.ViewSideEffect
import com.mpapps.marvelcompose.ui.views.charactersList.model.CharactersUi

sealed class CharactersListEffect : ViewSideEffect {
    sealed class Navigation : CharactersListEffect() {
        data class NavigateToDetail(val charactersUi: CharactersUi): Navigation()
    }
}