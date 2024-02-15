package com.mpapps.marvelcompose.ui.infrastructure

 abstract class UiState(
     open var isLoading: Boolean,
     open var isError: Boolean
)