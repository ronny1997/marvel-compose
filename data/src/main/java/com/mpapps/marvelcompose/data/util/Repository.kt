package com.mpapps.marvelcompose.data.util

import com.mpapps.marvelcompose.data.infrastructure.error.UnauthorizedException
import com.mpapps.marvelcompose.domain.infrastructure.error.NotFoundRepositoryException
import com.mpapps.marvelcompose.domain.infrastructure.error.UnauthorizedRepositoryException

abstract class Repository {

    suspend fun <T> safeExecution(execution: suspend () -> T): T {
        return try {
            execution.invoke()
        } catch (ex: Exception) {
            when (ex) {
                is UnauthorizedException -> throw UnauthorizedRepositoryException(ex.message)
                else -> throw NotFoundRepositoryException(ex.message ?: "")
            }
        }
    }
}