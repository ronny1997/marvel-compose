package com.mpapps.marvelcompose.data.model.map

import com.mpapps.marvelcompose.data.model.CharactersDto
import com.mpapps.marvelcompose.data.model.ComicDto
import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.domain.model.Comic

fun CharactersDto.toDomain() = Characters(
    id = id, name = name,
    description = description,
    bitmapThumbnail = null,
    thumbnailUrl = thumbnail
)

fun ComicDto.toDomain() = Comic(title = title, thumbnail = thumbnail)