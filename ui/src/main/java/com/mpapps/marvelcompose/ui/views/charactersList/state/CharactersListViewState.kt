package com.mpapps.marvelcompose.ui.views.charactersList.state

import com.mpapps.marvelcompose.ui.infrastructure.UiState
import com.mpapps.marvelcompose.ui.infrastructure.ViewState
import com.mpapps.marvelcompose.ui.views.charactersList.model.CharactersUi

data class CharactersListViewState(
    val data: List<CharactersUi> = listOf(),
    override var isLoading: Boolean = false,
    override var isError: Boolean = false
) : ViewState, UiState(isLoading, isError)