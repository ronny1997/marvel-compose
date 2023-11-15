package com.mpapps.marvelcompose.framework.datasources.cloud.response.map

import com.mpapps.marvelcompose.data.model.CharactersDto
import com.mpapps.marvelcompose.framework.datasources.cloud.response.MarvelApiResponse

fun MarvelApiResponse.toDto(): List<CharactersDto> = this.data.results.map {
    CharactersDto(
        id = it.id,
        name = it.name,
        description = it.description,
        thumbnail = it.thumbnail.path + "." + it.thumbnail.extension
    )
}