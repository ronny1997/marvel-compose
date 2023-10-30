package com.mpapps.marvelcompose.framework.datasources.cloud.map

import com.mpapps.marvelcompose.data.remote.model.CharactersDto
import com.mpapps.marvelcompose.framework.datasources.cloud.response.CharactersResponse

fun CharactersResponse.toDto(): CharactersDto = CharactersDto()