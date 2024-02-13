package com.mpapps.marvelcompose.framework.datasources.cloud

import com.mpapps.marvelcompose.data.dataSource.MarvelDataSource
import com.mpapps.marvelcompose.data.model.CharactersDto
import com.mpapps.marvelcompose.data.model.ComicDto
import com.mpapps.marvelcompose.framework.datasources.cloud.api.MarvelApi
import com.mpapps.marvelcompose.framework.datasources.cloud.response.map.toDto
import com.mpapps.marvelcompose.framework.infrastructure.util.ApiCallData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarvelDataSourceImpl @Inject constructor(
    private val marvelApi: MarvelApi,
) : MarvelDataSource {

    override suspend fun getCharacters(offset : Int,  limit: Int): Flow<List<CharactersDto>> {
        val apiCallData = ApiCallData()
        return marvelApi.getCharacters(
            ts = apiCallData.milliseconds,
            apikey = apiCallData.apiKey,
            hash = apiCallData.calculateMD5Hash(),
            limit = limit,
            offset = offset
        ).map {
            it.toDto()
        }
    }

    override suspend fun getComicsFromCharacter(
        offset: Int,
        limit: Int,
        charactersId: String
    ): Flow<List<ComicDto>> {
        val apiCallData = ApiCallData()
        return marvelApi.getComics(
            ts = apiCallData.milliseconds,
            apikey = apiCallData.apiKey,
            hash = apiCallData.calculateMD5Hash(),
            limit = limit,
            offset = offset,
            characterId = charactersId
        ).map {
            it.toDto()
        }
    }

}