package com.mpapps.marvelcompose.ui.views.charactersList.viewmodel

import androidx.lifecycle.viewModelScope
import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.domain.usecase.GetCharactersUseCase
import com.mpapps.marvelcompose.ui.infrastructure.BaseViewModel
import com.mpapps.marvelcompose.ui.views.charactersList.state.CharactersListEffect
import com.mpapps.marvelcompose.ui.views.charactersList.state.CharactersListEvent
import com.mpapps.marvelcompose.ui.views.charactersList.state.CharactersListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CharactersListViewModel @Inject constructor(
    private val charactersUseCase: GetCharactersUseCase,
) : BaseViewModel<CharactersListEffect, CharactersListEvent, CharactersListViewState>() {

    init {
        if (uiState.data.isEmpty()) {
            getCharacters()
        }
    }

    override fun onEvent(event: CharactersListEvent) {
        when (event) {
            is CharactersListEvent.GetCharacters -> getCharacters()
            is CharactersListEvent.NavigationToDetail -> goToDetail(event.charactersUi)
        }
    }

    private fun goToDetail(charactersUi: Characters) {
        setEffect {
            CharactersListEffect.Navigation.NavigateToDetail(charactersUi)
        }
    }

    override fun setInitialState(): CharactersListViewState {
        return CharactersListViewState()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            if (!uiState.isLoading) {
                setState {
                    copy(
                        isLoading = true
                    )
                }
                charactersUseCase()
                    .collectLatest { result ->
                        result.fold(::handleError) { data ->
                            setState {
                                copy(
                                    data = uiState.data + data,
                                    isLoading = false
                                )
                            }
                        }
                    }
            }
        }
    }

}