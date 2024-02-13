package com.mpapps.marvelcompose.ui.views.questions.state

import com.mpapps.marvelcompose.ui.infrastructure.Event

sealed class CharacterDetailEvent : Event {
    class GetComics(val characterId: String) : CharacterDetailEvent()
}