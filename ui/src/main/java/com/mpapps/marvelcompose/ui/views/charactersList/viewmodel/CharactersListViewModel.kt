package com.mpapps.marvelcompose.ui.views.charactersList.viewmodel

import androidx.lifecycle.viewModelScope
import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.domain.usecase.GetCharactersUseCase
import com.mpapps.marvelcompose.ui.infrastructure.BaseViewModel
import com.mpapps.marvelcompose.ui.views.charactersList.model.CharactersUi
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
            is CharactersListEvent.UpdatePainterImage -> updatePainterImage(event.updateCharactersUi)
        }
    }

    private fun goToDetail(charactersUi: CharactersUi) {
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
                            handleResult(data)
                        }
                    }
            }
        }
    }

    private fun handleResult(data: List<Characters>) {
        val charactersUI = data.map {
            CharactersUi(
                id = it.id,
                name = it.name,
                description = it.description,
                thumbnail = it.thumbnail
            )
        }
        val updatedData = uiState.data.let { currentData ->
            currentData.toMutableList().apply {
                addAll(charactersUI)
            }
        }

        setState {
            copy(
                data = updatedData,
                isLoading = false
            )
        }
    }

    private fun updatePainterImage(updateCharactersUi: CharactersUi) {
        viewModelScope.launch {
            val updatedData = uiState.data.map { character ->
                if (character.id == updateCharactersUi.id) {
                    updateCharactersUi
                } else {
                    character
                }
            }
            setState {
                copy(
                    data = updatedData
                )
            }
        }
    }
}