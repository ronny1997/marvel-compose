package com.mpapps.marvelcompose.data.infrastructure.error

sealed class DataException(message: String? = null, open var code: String = "") :
    RuntimeException(message) {
    class UnauthorizedException(message: String? = null, code: String = "") :
        DataException()

    class NotFoundException(override val message: String? = null, override var code: String = "") :
        DataException()

    override fun toString(): String {
        return "DataException(code='$code', message='$message)"
    }
}