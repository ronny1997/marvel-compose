package com.mpapps.marvelcompose.data.remote

import com.mpapps.marvelcompose.data.remote.model.CharactersDto

interface MarvelDataSource {
    suspend fun getCharacters(): CharactersDto
}