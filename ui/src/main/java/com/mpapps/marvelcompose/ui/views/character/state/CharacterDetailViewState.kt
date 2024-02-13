package com.mpapps.marvelcompose.ui.views.character.state

import com.mpapps.marvelcompose.domain.model.Comic
import com.mpapps.marvelcompose.ui.infrastructure.UiState
import com.mpapps.marvelcompose.ui.infrastructure.ViewState
import com.mpapps.marvelcompose.ui.views.charactersList.model.CharactersUi

data class CharacterDetailViewState(
    val character: CharactersUi?,
    val comicList: List<Comic> = listOf(),
    override var isLoading: Boolean = false,
    override var isError: Boolean = false
) : ViewState, UiState(isLoading, isError)