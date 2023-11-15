package com.mpapps.marvelcompose.domain.repository

import com.mpapps.marvelcompose.domain.model.Characters
import kotlinx.coroutines.flow.Flow

interface MarvelRepository {
    suspend fun getCharacterList(): Flow<List<Characters>>
}