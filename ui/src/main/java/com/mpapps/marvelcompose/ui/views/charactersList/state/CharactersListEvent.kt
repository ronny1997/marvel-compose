package com.mpapps.marvelcompose.ui.views.charactersList.state

import com.mpapps.marvelcompose.ui.infrastructure.EventState
import com.mpapps.marvelcompose.ui.views.charactersList.model.CharactersUi

sealed class CharactersListEvent : EventState {
    object GetCharacters : CharactersListEvent()
    data class NavigationToDetail(val charactersUi: CharactersUi) : CharactersListEvent()

    data class UpdatePainterImage(val updateCharactersUi: CharactersUi) : CharactersListEvent()
}