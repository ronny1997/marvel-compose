package com.patric.ui.login

import com.patric.core.base.Event

sealed class LoginEvent : Event {
    class Login(val account: String) : LoginEvent()
}
