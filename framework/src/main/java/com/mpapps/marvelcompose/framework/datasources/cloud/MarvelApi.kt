package com.mpapps.marvelcompose.framework.datasources.cloud

import com.mpapps.marvelcompose.framework.datasources.cloud.response.CharactersResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class MarvelApi @Inject constructor(private val client: HttpClient) {

    suspend fun getCharacters(

    ): CharactersResponse {
        return client.get("v1/public/characters") {
            parameter("offset", "value")
            parameter("limit", "value")
            parameter("ts", "value")
            parameter("apikey", "value")
            parameter("hash", "value")
        }.body()
    }
}