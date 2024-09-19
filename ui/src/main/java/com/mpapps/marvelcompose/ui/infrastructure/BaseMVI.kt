package com.mpapps.marvelcompose.ui.infrastructure

interface ViewSideEffect

interface ViewState

interface EventState

typealias OnAction = (EventState) -> Unit

const val SIDE_EFFECTS_KEY = "side-effects_key"