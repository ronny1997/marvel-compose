package com.mpapps.marvelcompose.ui.views.players.state

import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.ui.infrastructure.ViewState

data class CharacterState(
    val data: List<Characters> = listOf()
): ViewState