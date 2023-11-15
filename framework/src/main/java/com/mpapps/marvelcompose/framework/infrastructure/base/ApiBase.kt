package com.mpapps.marvelcompose.framework.infrastructure.base

import com.mpapps.marvelcompose.data.infrastructure.error.DataException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


abstract class ApiBase {

    protected inline fun <reified T> runRequest(
        httpResponse: HttpResponse
    ): Flow<T> = flow {
        when (httpResponse.status) {
            HttpStatusCode.OK -> emit(httpResponse.call.body())
            HttpStatusCode.Unauthorized -> throw DataException.UnauthorizedException(httpResponse.status.description)
            else -> throw DataException.NotFoundException(httpResponse.status.description)
        }
    }

}