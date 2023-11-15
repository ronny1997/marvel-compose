package com.mpapps.marvelcompose.ui.views.players.viewmodel

import com.mpapps.marvelcompose.domain.DomainResult
import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.domain.usecase.GetCharactersUseCase
import com.mpapps.marvelcompose.ui.infrastructure.BaseViewModel
import com.mpapps.marvelcompose.ui.infrastructure.UiGenericState
import com.mpapps.marvelcompose.ui.infrastructure.UiState
import com.mpapps.marvelcompose.ui.views.players.state.CharacterEvent
import com.mpapps.marvelcompose.ui.views.players.state.CharacterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CharactersViewModel @Inject constructor(
    private val charactersUseCase: GetCharactersUseCase
) : BaseViewModel<CharacterState, CharacterEvent>() {

    override val specificUiState: CharacterState
        get() = CharacterState()

    override fun onEvent(event: CharacterEvent) {
        when (event) {
            CharacterEvent.GetCharacters -> getCharacters()
        }
    }

    private fun getCharacters() {
        launch {
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

    private fun handleResult(data: List<Characters>): UiGenericState<CharacterState> {
        val updatedData = uiState.specificState?.data?.let { currentData ->
            currentData.toMutableList().apply {
                addAll(data)
            }
        }
        val newSpecificState = CharacterState(data = updatedData ?: data)
        return UiGenericState(
            specificState =newSpecificState,
            generalState = UiState(isLoading = false)
        )
    }

}