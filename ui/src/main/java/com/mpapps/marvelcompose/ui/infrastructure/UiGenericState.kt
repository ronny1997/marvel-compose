package com.mpapps.marvelcompose.ui.infrastructure

import com.mpapps.marvelcompose.ui.infrastructure.error.UiError

abstract class UiState(
     open var isLoading: Boolean,
     open var uiError: UiError?
)