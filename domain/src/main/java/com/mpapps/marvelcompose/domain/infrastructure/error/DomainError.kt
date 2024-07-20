package com.mpapps.marvelcompose.domain.infrastructure.error

sealed class DomainError(val message: String = "Generic error") {
    class NoConnectionError(message: String = "Not conection") : DomainError(message)
    class NotFoundError(message: String = "Not found error") : DomainError(message)
    object GenericError : DomainError()
}