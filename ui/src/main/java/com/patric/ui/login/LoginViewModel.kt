package com.patric.ui.login

import com.patric.core.base.BaseViewModel
import com.patric.core.domain.error.CustomError
import com.patric.core.domain.fold
import com.patric.core.settings.SettingsApp
import com.patric.core.utils.ErrorApp
import com.patric.core.utils.Navigator
import com.patric.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val settings: SettingsApp,
    private val loginUseCase: LoginUseCase,
    private val navigator: Navigator,
    private val errorApp: ErrorApp,
) : BaseViewModel<LoginState, LoginEvent>(LoginState()) {

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> login()
        }
    }


    private fun login() {
        launch {
            updateViewState(viewState.value.copy(isLoading = true))
            loginUseCase("").fold(
                onSuccess = {

                },
                onFailure = ::errorLogin
            )
        }
    }

    private fun errorLogin(customError: CustomError) {
        updateViewState(viewState.value.copy(isLoading = false))
        errorApp.showError(customError)
    }
}