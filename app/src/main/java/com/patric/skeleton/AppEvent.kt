package com.patric.skeleton

import com.patric.core.base.Event

sealed class AppEvent : Event {
    object Logout : AppEvent()
}