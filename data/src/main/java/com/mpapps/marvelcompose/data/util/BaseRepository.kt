package com.mpapps.marvelcompose.data.util

import com.mpapps.marvelcompose.data.infrastructure.error.DataException
import com.mpapps.marvelcompose.domain.infrastructure.error.RepositoryException

abstract class BaseRepository {

    suspend fun <T> safeExecution(execution: suspend () -> T): T {
        return try {
            execution.invoke()
        } catch (ex: Exception) {
            when (ex) {
                is DataException.UnauthorizedException -> throw RepositoryException.UnauthorizedRepositoryException(ex.message)
                else -> throw RepositoryException.NotFoundRepositoryException(ex.message ?: "")
            }
        }
    }
}