package com.mpapps.marvelcompose.domain.usecase

import com.mpapps.marvelcompose.domain.infrastructure.base.BaseUseCase
import com.mpapps.marvelcompose.domain.model.Comic
import com.mpapps.marvelcompose.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetComicFromCharacterUseCase @Inject constructor(
    private val repository: MarvelRepository
) : BaseUseCase<List<Comic>, String>() {
    override suspend fun runInBackground(params: String): Flow<List<Comic>> {
        return repository.getComicFromCharacterList(params)
    }
}