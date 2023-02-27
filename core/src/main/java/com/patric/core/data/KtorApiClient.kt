package com.patric.core.data

import com.patric.core.settings.SettingsApp
import com.patric.core.settings.SettingsApp.Companion.BASE_URL
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import javax.inject.Inject


class KtorApiClient @Inject constructor(
    val httpClient: HttpClient,
    val settingsApp: SettingsApp
) {

    suspend inline fun <reified T> get(path: String, params: StringValues = StringValues.Empty): T {
        val baseUrl = settingsApp.get(BASE_URL)
        return httpClient.get {
            url {
                if (baseUrl != null) host = baseUrl
                encodedPath = path
                parameters.appendAll(params)
            }
        }.body()
    }

    suspend inline fun <reified T> post(
        path: String,
        requestBody: FormDataContent
    ): T = httpClient.post() {
        val baseUrl = settingsApp.get(BASE_URL)
        url {
            if (baseUrl != null) host = baseUrl
            encodedPath = path
            contentType(ContentType.Application.FormUrlEncoded)
        }
        setBody(requestBody)
    }.body()

}