package com.patric.core.domain

import com.patric.core.domain.error.CustomError

sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Failure<out T>(val customError: CustomError) : Result<T>()
}

fun <T> successOf(value: T) = Result.Success(value)
fun <T> failureOf(customError: CustomError) = Result.Failure<T>(customError)

inline fun <R, T> Result<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: CustomError) -> R
): R = when (this) {
    is Result.Success -> onSuccess(value)
    is Result.Failure -> onFailure(customError)
}