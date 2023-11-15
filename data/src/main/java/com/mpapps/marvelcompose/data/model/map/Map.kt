package com.mpapps.marvelcompose.data.model.map

import com.mpapps.marvelcompose.data.model.CharactersDto
import com.mpapps.marvelcompose.domain.model.Characters

fun CharactersDto.toDomain() = Characters(
    id = id, name = name,
    description = description,
    thumbnail = thumbnail
)