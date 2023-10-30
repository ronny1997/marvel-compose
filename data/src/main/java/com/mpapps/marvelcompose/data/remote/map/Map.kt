package com.mpapps.marvelcompose.data.remote.map

import com.mpapps.marvelcompose.data.remote.model.CharactersDto
import com.mpapps.marvelcompose.domain.model.Characters

fun CharactersDto.toDomain() = Characters()