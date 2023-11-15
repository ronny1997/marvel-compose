package com.mpapps.marvelcompose.ui.infrastructure

internal data class UiGenericState<T : ViewState>(
    val generalState: UiState = UiState(isLoading = false, isError = false),
    val specificState: T? = null,
)

internal data class UiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false
)