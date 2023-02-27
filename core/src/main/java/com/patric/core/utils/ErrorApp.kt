package com.patric.core.utils

import com.patric.core.domain.error.CustomError
import kotlinx.coroutines.flow.*

class ErrorApp {
    private val _sharedFlow = MutableStateFlow<CustomError>(CustomError.Generic(""))

    val viewState: StateFlow<CustomError?>
        get() = _sharedFlow

    fun showError(customError: CustomError) {
        _sharedFlow.tryEmit(customError)
    }
}