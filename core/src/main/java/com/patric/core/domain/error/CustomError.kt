package com.patric.core.domain.error

import com.patric.core.data.ErrorResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


sealed class CustomError(override val message: String?) : Throwable(message) {

    val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true
        useAlternativeNames = true
    }

    sealed class Repository(message: String? = null) : CustomError(message) {
        class Http(val code: Int, message: String?) : Repository(message) {
            override val message: String
                get() = "Http error code: $code - Message: ${super.message}"
        }

        class BadRequest(val bodyText: String) : Repository() {
            override val message: String
                get() = json.decodeFromString<ErrorResponse>(ErrorResponse.transformToError(bodyText))
                    .messageError()
        }

        object Unauthorized : Repository()
        object Forbidden : Repository()
    }

    class Generic(message: String?, val stackTrace: String? = null) : CustomError(message)
    object None : CustomError("")
}

sealed class ApiCustomError(override val message: String) : CustomError(message) {
    class FieldCustomError(val name: String, val code: String, override val message: String) :
        ApiCustomError(message)

    class MessageCustomError(override val message: String) : ApiCustomError(message)
}
