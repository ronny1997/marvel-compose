package com.mpapps.marvelcompose.framework.datasources.cloud

import com.mpapps.marvelcompose.data.remote.MarvelDataSource
import com.mpapps.marvelcompose.data.remote.model.CharactersDto
import com.mpapps.marvelcompose.framework.datasources.cloud.map.toDto
import javax.inject.Inject

class MarvelDataSourceImpl @Inject constructor(
    private val marvelApi: MarvelApi
) : MarvelDataSource {

    override suspend fun getCharacters(): CharactersDto {
        return marvelApi.getCharacters(
            ts = "1643971246269",
            apikey = "40c0f1172b2c2c7fc17e73d2d7a8b016",
            hash = "d20cada3f36a6b00f16296f6087259c9"
        ).toDto()

    }
}