package com.mpapps.marvelcompose.domain.infrastructure.error

sealed interface DomainError {
    object NoConnectionError : DomainError
    object NotFoundError: DomainError
}