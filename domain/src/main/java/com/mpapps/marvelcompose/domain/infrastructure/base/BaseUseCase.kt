package com.mpapps.marvelcompose.domain.infrastructure.base

import com.mpapps.marvelcompose.domain.DomainResult
import com.mpapps.marvelcompose.domain.infrastructure.error.DomainError
import com.mpapps.marvelcompose.domain.infrastructure.error.RepositoryException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

@Suppress("UNCHECKED_CAST")
abstract class BaseUseCase<out T : Any, in Params> {

    protected abstract suspend fun runInBackground(params: Params): Flow<T>

     suspend operator fun invoke(params: Params? = null): Flow<DomainResult<T, DomainError>> {
         return runInBackground(params ?: Unit as Params)
             .map<T, DomainResult<T, DomainError>> { result ->
                 DomainResult.Success(result)
             }
             .catch { e ->
                 emit(DomainResult.Error(handleError(e as RuntimeException)))
             }
             .flowOn(Dispatchers.IO)
    }

    protected open fun handleError(e: RuntimeException): DomainError {
        return when (e) {
            is RepositoryException.UnauthorizedRepositoryException -> DomainError.NoConnectionError()
            is RepositoryException.NotFoundRepositoryException -> DomainError.NotFoundError()
            else -> DomainError.GenericError
        }
    }
}