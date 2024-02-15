package com.mpapps.marvelcompose.ui.views.charactersList.state

import android.graphics.drawable.Drawable
import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.ui.infrastructure.EventState

sealed class CharactersListEvent : EventState {
    object GetCharacters : CharactersListEvent()
    data class NavigationToDetail(val charactersUi: Characters) : CharactersListEvent()
}