package com.mpapps.marvelcompose.framework.datasources.cloud.api

import com.mpapps.marvelcompose.framework.datasources.cloud.response.MarvelApiResponse
import com.mpapps.marvelcompose.framework.datasources.cloud.response.comics.MarvelComicApiResponse
import com.mpapps.marvelcompose.framework.infrastructure.base.ApiBase
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarvelApiImpl @Inject constructor(private val client: HttpClient) : ApiBase(), MarvelApi {

    override suspend fun getCharacters(
        ts: String,
        apikey: String,
        hash: String,
        limit: Int,
        offset: Int
    ): Flow<MarvelApiResponse> {
        return runRequest(
            client.get("v1/public/characters") {
                parameter("ts", ts)
                parameter("apikey", apikey)
                parameter("hash", hash)
                parameter("limit", limit)
                parameter("offset", offset)
            }
        )
    }

    override suspend fun getComics(
        ts: String,
        apikey: String,
        hash: String,
        limit: Int,
        offset: Int,
        characterId: String
    ): Flow<MarvelComicApiResponse> {
        return runRequest(
            client.get("v1/public/characters/${characterId}/comics") {
                parameter("ts", ts)
                parameter("apikey", apikey)
                parameter("hash", hash)
                parameter("limit", limit)
                parameter("offset", offset)
            }
        )
    }

}

