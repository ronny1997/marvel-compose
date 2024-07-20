package com.mpapps.marvelcompose.ui.views.character.viewmodel

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.domain.usecase.GetComicFromCharacterUseCase
import com.mpapps.marvelcompose.ui.infrastructure.BaseViewModel
import com.mpapps.marvelcompose.ui.infrastructure.error.UiError
import com.mpapps.marvelcompose.ui.views.character.state.CharacterDetailEffect
import com.mpapps.marvelcompose.ui.views.character.state.CharacterDetailEvent
import com.mpapps.marvelcompose.ui.views.character.state.CharacterDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CharacterDetailViewModel @Inject constructor(
    private val getComicFromCharacterUseCase: GetComicFromCharacterUseCase,
) : BaseViewModel<CharacterDetailEffect, CharacterDetailEvent, CharacterDetailViewState>() {

    override fun setInitialState(): CharacterDetailViewState {
        return CharacterDetailViewState(character = null)
    }

    override fun onEvent(event: CharacterDetailEvent) {
        when (event) {
            is CharacterDetailEvent.InitData -> initData(event.jsonCharacter)
        }
    }

    private fun initData(jsonCharacter: String) {
        val decodeJson = Uri.decode(jsonCharacter)
        val characters = Gson().fromJson(decodeJson, Characters::class.java)
        setState {
            copy(character = characters)
        }
        getComics(characters.id)
    }

    private fun getComics(characterId: String) {
        viewModelScope.launch {
            getComicFromCharacterUseCase(characterId).collectLatest { result ->
                result.fold({
                    handleError(it) {
                        setState {
                            copy(
                                isLoading = false,
                                uiError = UiError(it),
                            )
                        }
                    }
                }) { data ->
                    setState {
                        copy(comicList = data)
                    }
                }
            }
        }
    }
}