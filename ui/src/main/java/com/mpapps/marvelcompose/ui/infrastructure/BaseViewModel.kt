package com.mpapps.marvelcompose.ui.infrastructure

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mpapps.marvelcompose.domain.infrastructure.error.DomainError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Effect : ViewSideEffect, Event : EventState, UiState : ViewState> : ViewModel() {

    abstract fun setInitialState(): UiState
    private val initialState: UiState by lazy { setInitialState() }
    var uiState by mutableStateOf(initialState)

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    abstract fun onEvent(event: Event)

    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = uiState.reducer()
        uiState = newState
    }

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                onEvent(it)
            }
        }
    }

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    protected fun handleError(domainError: DomainError, onError: (String) -> Unit) {
        when(domainError){
            is DomainError.GenericError -> onError(domainError.message)
            is DomainError.NoConnectionError -> onError(domainError.message)
            is DomainError.NotFoundError -> onError(domainError.message)

        }

    }
    /* protected fun handleError(domainError: DomainError): UiGenericState<VS> {
         return when (domainError) {
             is DomainError.GenericError -> {
                 UiGenericState(
                     generalState = uiState(isLoading = false, isError = true)
                 )
             }

             is DomainError.NoConnectionError -> {
                 UiGenericState(
                     generalState = UiState(isLoading = false, isError = true)
                 )
             }

             is DomainError.NotFoundError -> {
                 UiGenericState(
                     generalState = UiState(isLoading = false, isError = true)
                 )
             }
         }
     }*/
}