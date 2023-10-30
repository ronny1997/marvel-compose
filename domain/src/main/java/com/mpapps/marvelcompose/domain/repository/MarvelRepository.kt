package com.mpapps.marvelcompose.domain.repository

import com.mpapps.marvelcompose.domain.model.Characters

interface MarvelRepository {

    suspend fun getCharacterList(): Characters

}