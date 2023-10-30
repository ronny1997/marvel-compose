package com.mpapps.marvelcompose.framework.datasources.cloud

import com.mpapps.marvelcompose.framework.datasources.cloud.response.CharactersResponse
import com.mpapps.marvelcompose.framework.infrastructure.base.ApiBase
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class MarvelApi @Inject constructor(private val client: HttpClient) : ApiBase() {

    suspend fun getCharacters(
        ts: String,
        apikey: String,
        hash: String
    ): CharactersResponse {

        return runRequest(
            client.get("v1/public/characters") {
                parameter("ts", ts)
                parameter("apikey", apikey)
                parameter("hash", hash)
            }
        )
    }
}


