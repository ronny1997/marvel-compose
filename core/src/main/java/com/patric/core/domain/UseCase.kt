package com.patric.core.domain

import com.patric.core.dispacher.io
import com.patric.core.domain.error.CustomError
import kotlinx.coroutines.withContext

abstract class UseCase<Type, in Params> where Type : Any {

    protected abstract suspend fun run(params: Params): Type

    suspend operator fun invoke(params: Params): Result<Type> {
        return withContext(io) {
            try {
                successOf(run(params))
            } catch (e: CustomError) {
                failureOf(e)
            }
        }
    }

    object None
}