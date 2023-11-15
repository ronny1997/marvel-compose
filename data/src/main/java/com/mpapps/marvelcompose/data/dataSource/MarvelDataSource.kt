package com.mpapps.marvelcompose.data.dataSource

import com.mpapps.marvelcompose.data.model.CharactersDto
import kotlinx.coroutines.flow.Flow

interface MarvelDataSource {
    suspend fun getCharacters(offset: Int, limit: Int): Flow<List<CharactersDto>>
}