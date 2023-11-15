package com.mpapps.marvelcompose.domain

import com.mpapps.marvelcompose.domain.infrastructure.error.DomainError

sealed class DomainResult<out S : Any, out F : Any> {
    data class Success<out S : Any>(val data: S) : DomainResult<S, Nothing>()
    data class Error(val error: DomainError) : DomainResult<Nothing, DomainError>()
}