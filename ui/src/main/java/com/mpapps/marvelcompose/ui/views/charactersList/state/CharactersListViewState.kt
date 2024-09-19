package com.mpapps.marvelcompose.ui.views.charactersList.state

import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.ui.infrastructure.UiState
import com.mpapps.marvelcompose.ui.infrastructure.ViewState
import com.mpapps.marvelcompose.ui.infrastructure.error.UiError

data class CharactersListViewState(
    val data: MutableMap<String, Characters> = mutableMapOf(),
    override var isLoading: Boolean = false,
    override var uiError: UiError? = null
) : ViewState, UiState(isLoading, uiError)