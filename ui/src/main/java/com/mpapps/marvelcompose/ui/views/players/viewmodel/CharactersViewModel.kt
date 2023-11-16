package com.mpapps.marvelcompose.ui.views.players.viewmodel

import com.mpapps.marvelcompose.domain.DomainResult
import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.domain.usecase.GetCharactersUseCase
import com.mpapps.marvelcompose.ui.infrastructure.BaseViewModel
import com.mpapps.marvelcompose.ui.infrastructure.UiGenericState
import com.mpapps.marvelcompose.ui.infrastructure.UiState
import com.mpapps.marvelcompose.ui.views.players.model.CharactersUi
import com.mpapps.marvelcompose.ui.views.players.state.CharacterEvent
import com.mpapps.marvelcompose.ui.views.players.state.CharacterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CharactersViewModel @Inject constructor(
    private val charactersUseCase: GetCharactersUseCase,
) : BaseViewModel<CharacterState, CharacterEvent>() {

    init {
        if (uiState.specificState?.data.isNullOrEmpty()) {
            getCharacters()
        }
    }

    override val specificUiState: CharacterState
        get() = CharacterState()

    override fun onEvent(event: CharacterEvent) {
        when (event) {
            CharacterEvent.GetCharacters -> getCharacters()
        }
    }

    private fun getCharacters() {
        launch {
            if (!uiState.generalState.isLoading) {
                uiState = uiState.copy(
                    generalState = UiState(isLoading = true)
                )
                charactersUseCase()
                    .take(1)
                    .collectLatest {
                        uiState = when (it) {
                            is DomainResult.Success -> handleResult(it.data)
                            is DomainResult.Error -> handleError(it.error)
                        }
                    }
            }
        }
    }

    private fun handleResult(data: List<Characters>): UiGenericState<CharacterState> {
        val charactersUI = data.map {
            CharactersUi(
                id = it.id,
                name = it.name,
                description = it.description,
                thumbnail = it.thumbnail
            )
        }
        val updatedData = uiState.specificState?.data?.let { currentData ->
            currentData.toMutableList().apply {
                addAll(charactersUI)
            }
        }
        val newSpecificState = CharacterState(data = updatedData ?: charactersUI)
        return UiGenericState(
            specificState = newSpecificState,
            generalState = UiState(isLoading = false)
        )
    }

    fun updatePainterImage(updateCharactersUi: CharactersUi) {
        launch {
            val updatedData = uiState.specificState?.data?.map { character ->
                if (character.id == updateCharactersUi.id) {
                    updateCharactersUi
                } else {
                    character
                }
            } ?: listOf()
            val newSpecificState = uiState.specificState?.copy(data = updatedData)

            uiState = uiState.copy(
                specificState = newSpecificState
            )
        }
    }
}