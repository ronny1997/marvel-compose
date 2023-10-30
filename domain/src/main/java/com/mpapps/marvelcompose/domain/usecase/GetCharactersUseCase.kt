package com.mpapps.marvelcompose.domain.usecase

import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.domain.repository.MarvelRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: MarvelRepository
) : BaseUseCase<Characters, Unit>() {
    override suspend fun runInBackground(params: Unit): Characters {
        return repository.getCharacterList()
    }
}