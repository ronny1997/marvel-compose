package com.mpapps.marvelcompose.ui.views.questions.viewmodel

import androidx.lifecycle.ViewModel
import com.mpapps.marvelcompose.domain.DomainResult
import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.domain.model.Comic
import com.mpapps.marvelcompose.domain.usecase.GetComicFromCharacterUseCase
import com.mpapps.marvelcompose.ui.infrastructure.BaseViewModel
import com.mpapps.marvelcompose.ui.infrastructure.UiGenericState
import com.mpapps.marvelcompose.ui.infrastructure.UiState
import com.mpapps.marvelcompose.ui.views.players.state.CharacterEvent
import com.mpapps.marvelcompose.ui.views.players.state.CharacterState
import com.mpapps.marvelcompose.ui.views.questions.state.CharacterDetailEvent
import com.mpapps.marvelcompose.ui.views.questions.state.CharacterDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CharactersDetailViewModel @Inject constructor(
    private val getComicFromCharacterUseCase: GetComicFromCharacterUseCase,
) : BaseViewModel<CharacterDetailState, CharacterDetailEvent>() {

    override val specificUiState: CharacterDetailState
        get() = CharacterDetailState()

    override fun onEvent(event: CharacterDetailEvent) {
        when (event) {
            is CharacterDetailEvent.GetComics -> getComics(event.characterId)
        }
    }

    private fun getComics(characterId: String) {
        launch {
            uiState = uiState.copy(
                generalState = UiState(isLoading = true)
            )
            getComicFromCharacterUseCase(characterId)
                .take(1)
                .collectLatest {
                    uiState = when (it) {
                        is DomainResult.Success -> handleResult(it.data)
                        is DomainResult.Error -> handleError(it.error)
                    }
                }
        }
    }

    private fun handleResult(data: List<Comic>): UiGenericState<CharacterDetailState> {
        val characterDetailState = CharacterDetailState(data)
        return UiGenericState(
            specificState = characterDetailState,
            generalState = UiState(isLoading = false)
        )
    }

}