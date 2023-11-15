package com.mpapps.marvelcompose.ui.infrastructure

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mpapps.marvelcompose.domain.infrastructure.error.DomainError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

internal abstract class BaseViewModel<VS : ViewState, I : Event> : ViewModel(), CoroutineScope {


    private val parentJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Main + coroutineErrorHandler + getJob()


    private val coroutineErrorHandler = CoroutineExceptionHandler { _, exception ->
        handleError(exception)

    }

    protected abstract val specificUiState: VS
    var uiState by mutableStateOf(
        UiGenericState(
            generalState = UiState(isLoading = false),
            specificState = specificUiState,
        )
    )
        protected set

    abstract fun onEvent(event: I)

    private fun getJob() = if (parentJob.isCancelled || parentJob.isCompleted) {
        SupervisorJob()
    } else {
        parentJob
    }

    private fun handleError(t: Throwable?) {
        handleError(DomainError.GenericError(t?.message))
    }

    protected fun handleError(domainError: DomainError): UiGenericState<VS> {
       return when (domainError) {
            is DomainError.GenericError -> {
                UiGenericState(
                    generalState = UiState(isLoading = false, isError = true)
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
    }
}