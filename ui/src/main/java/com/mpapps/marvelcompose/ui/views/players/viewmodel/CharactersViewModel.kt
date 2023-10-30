package com.mpapps.marvelcompose.ui.views.players.viewmodel

import com.mpapps.marvelcompose.domain.usecase.GetCharactersUseCase
import com.mpapps.marvelcompose.ui.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersUseCase: GetCharactersUseCase
) : BaseViewModel() {


    fun getCharacters() {
        launch {
            charactersUseCase().fold(::handleError) {
                it
            }
        }
    }

}