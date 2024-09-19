package com.mpapps.marvelcompose.ui.views.character.state

import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.domain.model.Comic
import com.mpapps.marvelcompose.ui.infrastructure.UiState
import com.mpapps.marvelcompose.ui.infrastructure.ViewState
import com.mpapps.marvelcompose.ui.infrastructure.error.UiError

data class CharacterDetailViewState(
    val character: Characters?,
    val comicList: List<Comic> = listOf(),
    override var isLoading: Boolean = false,
    override var uiError: UiError? = null
) : ViewState, UiState(isLoading, uiError)