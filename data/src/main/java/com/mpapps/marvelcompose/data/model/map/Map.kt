package com.mpapps.marvelcompose.data.model.map

import android.graphics.Bitmap
import com.mpapps.marvelcompose.data.model.CharactersDto
import com.mpapps.marvelcompose.data.model.ComicDto
import com.mpapps.marvelcompose.domain.model.Characters
import com.mpapps.marvelcompose.domain.model.Comic

fun CharactersDto.toDomain(bitmap: Bitmap?) = Characters(
    id = id, name = name,
    description = description,
    bitmapThumbnail = bitmap,
)

fun ComicDto.toDomain() = Comic(title = title, thumbnail = thumbnail)