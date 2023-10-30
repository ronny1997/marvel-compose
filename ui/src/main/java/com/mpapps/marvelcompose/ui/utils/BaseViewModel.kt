package com.mpapps.marvelcompose.ui.utils

import androidx.lifecycle.ViewModel
import com.mpapps.marvelcompose.domain.infrastructure.error.DomainError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {


    private val parentJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Main + coroutineErrorHandler + getJob()


    private val coroutineErrorHandler = CoroutineExceptionHandler { _, exception ->
        DomainError.NoConnectionError
    }

    private fun getJob() = if (parentJob.isCancelled || parentJob.isCompleted) {
        SupervisorJob()
    } else {
        parentJob
    }


    protected fun handleError(domainError: DomainError){

    }
}