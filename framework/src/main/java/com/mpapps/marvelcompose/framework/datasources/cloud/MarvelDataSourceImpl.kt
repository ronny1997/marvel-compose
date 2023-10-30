package com.mpapps.marvelcompose.framework.datasources.cloud

import com.mpapps.marvelcompose.data.remote.MarvelDataSource
import com.mpapps.marvelcompose.data.remote.model.CharactersDto
import com.mpapps.marvelcompose.framework.datasources.cloud.map.toDto
import javax.inject.Inject

class MarvelDataSourceImpl @Inject constructor(
    private val marvelApi: MarvelApi
) : MarvelDataSource, CloudDataSourceImpl() {

    override suspend fun getCharacters(): CharactersDto {
        return execute {
            marvelApi.getCharacters().toDto()
        }
    }
}