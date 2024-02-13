package com.mpapps.marvelcompose.domain.repository

import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.domain.model.Comic
import kotlinx.coroutines.flow.Flow

interface MarvelRepository {
    suspend fun getCharacterList(): Flow<List<Characters>>
    suspend fun getComicFromCharacterList(characterId: String): Flow<List<Comic>>
}