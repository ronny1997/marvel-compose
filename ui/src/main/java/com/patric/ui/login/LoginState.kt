package com.patric.ui.login

import com.patric.core.base.ViewState

data class LoginState(
    val invalidForm: InvalidForm? = null,
    val showUrl: String = "",
    val isLoading: Boolean = false,
    val showUrlConfigBtn: Boolean = true // Depends on BuildConfig.FLAVOR == "transfesaUat"
) : ViewState

data class InvalidForm(
    val accountError: Boolean = false,
    val usernameError: Boolean = false,
    val passwordError: Boolean = false,
    val deviceError: Boolean = false
)
