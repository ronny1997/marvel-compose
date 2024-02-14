package com.mpapps.marvelcompose.ui.views.charactersList.state

import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.ui.infrastructure.UiState
import com.mpapps.marvelcompose.ui.infrastructure.ViewState

data class CharactersListViewState(
    val data: List<Characters> = listOf(),
    override var isLoading: Boolean = false,
    override var isError: Boolean = false
) : ViewState, UiState(isLoading, isError)