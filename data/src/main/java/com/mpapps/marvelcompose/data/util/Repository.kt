package com.mpapps.marvelcompose.data.util

abstract class Repository {

    suspend fun <T> safeExecution(execution: suspend () -> T): T {
        return try {
            execution.invoke()
        } catch (ex: Exception) {
            throw RepositoryException()
        }
    }


}