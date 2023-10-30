package com.mpapps.marvelcompose.domain.usecase

import com.mpapps.marvelcompose.domain.DomainResult
import com.mpapps.marvelcompose.domain.model.error.DomainError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Suppress("UNCHECKED_CAST")
abstract class BaseUseCase<out T : Any, in Params> {

    protected abstract suspend fun runInBackground(params: Params): T

    suspend operator fun invoke(params: Params? = null): DomainResult<T, DomainError> {
        return withContext(Dispatchers.IO) {
            try {
                val result = runInBackground(params ?: Unit as Params)
                DomainResult.Success(result)
            } catch (e: RuntimeException) {
                DomainResult.Error(handleError(e))
            }
        }
    }

    protected open fun handleError(e: RuntimeException): DomainError {
        return DomainError.NoConnectionError
    }

}