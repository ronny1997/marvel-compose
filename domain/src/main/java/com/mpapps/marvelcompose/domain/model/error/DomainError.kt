package com.mpapps.marvelcompose.domain.model.error

sealed interface DomainError {
    object NoConnectionError : DomainError
}