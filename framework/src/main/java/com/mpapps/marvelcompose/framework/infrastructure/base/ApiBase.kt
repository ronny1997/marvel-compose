package com.mpapps.marvelcompose.framework.infrastructure.base

import com.mpapps.marvelcompose.data.infrastructure.error.NotFoundException
import com.mpapps.marvelcompose.data.infrastructure.error.UnauthorizedException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

abstract class ApiBase() {

    suspend inline fun <reified T> runRequest(httpResponse: HttpResponse): T {
        return try {
            httpResponse.call.body()
        } catch (ex: Exception) {
            when (httpResponse.status) {
                HttpStatusCode.Unauthorized -> throw UnauthorizedException(ex.message ?: "")
                else -> throw NotFoundException(ex.message ?: "")
            }
        }
    }

}