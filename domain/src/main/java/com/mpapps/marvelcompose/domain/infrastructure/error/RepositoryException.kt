package com.mpapps.marvelcompose.domain.infrastructure.error
sealed class RepositoryException(open val messageDetail: String? = "") : RuntimeException() {
    class NotFoundRepositoryException(override val messageDetail: String?) :
        RepositoryException()
    class UnauthorizedRepositoryException(override val messageDetail: String?) :
        RepositoryException()
}