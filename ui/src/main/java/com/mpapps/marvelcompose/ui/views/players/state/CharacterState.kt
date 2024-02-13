package com.mpapps.marvelcompose.ui.views.players.state

import com.mpapps.marvelcompose.ui.infrastructure.ViewState
import com.mpapps.marvelcompose.ui.views.players.model.CharactersUi

data class CharacterState(
    val data: List<CharactersUi> = listOf()
): ViewState