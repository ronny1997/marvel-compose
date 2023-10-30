package com.mpapps.marvelcompose.domain.infrastructure.error


data class UnauthorizedRepositoryException(override val messageDetail: String) :
    RepositoryException()

data class NotFoundRepositoryException(override val messageDetail: String) :
    RepositoryException()

open class RepositoryException(open val messageDetail: String = "") : RuntimeException()