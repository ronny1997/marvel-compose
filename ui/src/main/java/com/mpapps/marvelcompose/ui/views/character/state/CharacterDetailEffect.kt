package com.mpapps.marvelcompose.ui.views.character.state

import com.mpapps.marvelcompose.ui.infrastructure.ViewSideEffect

sealed class CharacterDetailEffect : ViewSideEffect{
    sealed class Navigation : CharacterDetailEffect() {
        object NavigateTo : Navigation()
    }
}