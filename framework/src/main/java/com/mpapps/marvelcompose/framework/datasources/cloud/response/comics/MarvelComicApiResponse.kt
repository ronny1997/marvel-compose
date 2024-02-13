package com.mpapps.marvelcompose.framework.datasources.cloud.response.comics

import kotlinx.serialization.Serializable

@Serializable
data class MarvelComicApiResponse(
    val code: Int?,
    val data: ComicDataResponse
)