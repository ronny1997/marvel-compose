package com.mpapps.marvelcompose.ui.views.character.state

import com.mpapps.marvelcompose.ui.infrastructure.EventState
import com.mpapps.marvelcompose.ui.views.charactersList.model.CharactersUi

sealed class CharacterDetailEvent : EventState {
    class InitData(val jsonCharacter: String) : CharacterDetailEvent()
}