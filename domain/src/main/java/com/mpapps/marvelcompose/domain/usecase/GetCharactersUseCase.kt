package com.mpapps.marvelcompose.domain.usecase

import com.mpapps.marvelcompose.domain.infrastructure.base.BaseUseCase
import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: MarvelRepository
) : BaseUseCase<List<Characters>, Unit>() {
    override suspend fun runInBackground(params: Unit): Flow<List<Characters>> {
        return repository.getCharacterList()
    }
}