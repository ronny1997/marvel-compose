package com.mpapps.marvelcompose.ui.views.charactersList.state

import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.ui.infrastructure.ViewSideEffect

sealed class CharactersListEffect : ViewSideEffect {
    sealed class Navigation : CharactersListEffect() {
        data class NavigateToDetail(val charactersUi: Characters): Navigation()
    }
}