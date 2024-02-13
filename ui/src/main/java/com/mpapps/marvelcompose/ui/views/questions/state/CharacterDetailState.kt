package com.mpapps.marvelcompose.ui.views.questions.state

import com.mpapps.marvelcompose.domain.model.Comic
import com.mpapps.marvelcompose.ui.infrastructure.ViewState

data class CharacterDetailState(
    val data: List<Comic> = listOf()
) : ViewState