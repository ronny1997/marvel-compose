package com.mpapps.marvelcompose.ui.views.players.state

import com.mpapps.marvelcompose.ui.infrastructure.Event

sealed class CharacterEvent : Event {
    object GetCharacters : CharacterEvent()
}