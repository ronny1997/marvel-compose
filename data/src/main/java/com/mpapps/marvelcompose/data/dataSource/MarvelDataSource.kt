package com.mpapps.marvelcompose.data.dataSource

import com.mpapps.marvelcompose.data.model.CharactersDto
import com.mpapps.marvelcompose.data.model.ComicDto
import kotlinx.coroutines.flow.Flow

interface MarvelDataSource {
    suspend fun getCharacters(offset: Int, limit: Int): Flow<List<CharactersDto>>
    suspend fun getComicsFromCharacter(offset: Int, limit: Int, charactersId: String): Flow<List<ComicDto>>
}