package com.mpapps.marvelcompose.data.infrastructure.error

data class UnauthorizedException(override val message: String) : DataException(message)
data class NotFoundException(override val message: String) : DataException(message)

open class DataException(message: String? = null, open var code: String = "") :
    RuntimeException(message) {
    override fun toString(): String {
        return "DataException(code='$code', message='$message)"
    }
}