package com.mpapps.marvelcompose.framework.datasources.cloud.response.map

import com.mpapps.marvelcompose.data.model.CharactersDto
import com.mpapps.marvelcompose.data.model.ComicDto
import com.mpapps.marvelcompose.framework.datasources.cloud.response.MarvelApiResponse
import com.mpapps.marvelcompose.framework.datasources.cloud.response.comics.MarvelComicApiResponse

const val HTTP = "http"
const val HTTPS = "https"

fun MarvelApiResponse.toDto(): List<CharactersDto> = this.data.results.map {
    CharactersDto(
        id = it.id,
        name = it.name,
        description = it.description,
        thumbnail = (it.thumbnail.path + "." + it.thumbnail.extension).replace(HTTP, HTTPS)
    )
}

fun MarvelComicApiResponse.toDto(): List<ComicDto> = this.data.results.map {
    ComicDto(
        title = it.title,
        thumbnail = (it.thumbnail.path + "." + it.thumbnail.extension).replace(HTTP, HTTPS)
    )
}