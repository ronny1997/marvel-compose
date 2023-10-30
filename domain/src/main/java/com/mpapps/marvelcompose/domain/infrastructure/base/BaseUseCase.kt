package com.mpapps.marvelcompose.domain.infrastructure.base

import com.mpapps.marvelcompose.domain.DomainResult
import com.mpapps.marvelcompose.domain.infrastructure.error.NotFoundRepositoryException
import com.mpapps.marvelcompose.domain.infrastructure.error.UnauthorizedRepositoryException
import com.mpapps.marvelcompose.domain.infrastructure.error.DomainError
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
        return when (e) {
            is UnauthorizedRepositoryException -> DomainError.NoConnectionError
            is NotFoundRepositoryException -> DomainError.NotFoundError
            else -> DomainError.NotFoundError
        }
    }
}