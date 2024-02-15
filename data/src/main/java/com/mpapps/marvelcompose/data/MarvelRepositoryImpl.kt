package com.mpapps.marvelcompose.data

import com.mpapps.marvelcompose.data.dataSource.MarvelDataSource
import com.mpapps.marvelcompose.data.dataSource.NumCallApiCacheDataSource
import com.mpapps.marvelcompose.data.model.map.toDomain
import com.mpapps.marvelcompose.data.util.BaseRepository
import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.domain.model.Comic
import com.mpapps.marvelcompose.domain.repository.MarvelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val marvelDataSource: MarvelDataSource,
    private val numCallApiCacheDataSource: NumCallApiCacheDataSource,
) : MarvelRepository, BaseRepository() {

    companion object {
        const val LIMIT_CHARACTERS = 30
        const val ONE = 1
    }

    /**
     * Retrieves a flow of character data from the Marvel API, managing offsets
     * to retrieve different character sets based on the number of API calls made.
     *
     * @return Flow<List<Characters>> representing a stream of character data.
     */
    override suspend fun getCharacterList(): Flow<List<Characters>> {
        val numCallApi = numCallApiCacheDataSource.getNumCallApi() ?: 0
        val offset = numCallApi * LIMIT_CHARACTERS

        return safeExecution {
            val data = marvelDataSource.getCharacters(offset, LIMIT_CHARACTERS).map {
                it.map { charactersDto ->
                    charactersDto.toDomain()
                }
            }
            numCallApiCacheDataSource.setNumCallApi(numCallApi + ONE)
            data
        }
    }



    override suspend fun getComicFromCharacterList(characterId: String): Flow<List<Comic>> {
        val numCallApi = numCallApiCacheDataSource.getNumCallApi() ?: 0
        val offset = numCallApi * LIMIT_CHARACTERS
        return safeExecution {
            val data =
                marvelDataSource.getComicsFromCharacter(0, LIMIT_CHARACTERS, characterId).map {
                    it.map { comicDto ->
                        comicDto.toDomain()
                    }
                }
            numCallApiCacheDataSource.setNumCallApi(numCallApi + ONE)
            data
        }
    }
}