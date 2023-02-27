package com.patric.data.remote.datasource.api

import com.patric.core.data.KtorApiClient
import com.patric.data.remote.datasource.api.model.LoginResponse
import io.ktor.client.request.forms.*
import io.ktor.http.*
import javax.inject.Inject

class Api @Inject constructor(private val apiClient: KtorApiClient) {

    suspend fun login(
        request: String
    ) = apiClient.post<LoginResponse>(
        path = " ",
        requestBody = FormDataContent(Parameters.build {
            append("tenant", request)
        })
    )
}