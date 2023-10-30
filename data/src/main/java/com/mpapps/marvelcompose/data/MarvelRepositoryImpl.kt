package com.mpapps.marvelcompose.data

import com.mpapps.marvelcompose.data.remote.MarvelDataSource
import com.mpapps.marvelcompose.data.remote.map.toDomain
import com.mpapps.marvelcompose.data.util.Repository
import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.domain.repository.MarvelRepository
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val marvelDataSource: MarvelDataSource
) : MarvelRepository, Repository() {
    override suspend fun getCharacterList(): Characters {
        return safeExecution {
            marvelDataSource.getCharacters().toDomain()
        }
    }
}