package com.mpapps.marvelcompose.framework.datasources.cloud.api

import com.mpapps.marvelcompose.framework.datasources.cloud.response.MarvelApiResponse
import kotlinx.coroutines.flow.Flow

interface MarvelApi {
    suspend fun getCharacters(
        ts: String,
        apikey: String,
        hash: String,
        limit: Int,
        offset: Int,
    ): Flow<MarvelApiResponse>
}


