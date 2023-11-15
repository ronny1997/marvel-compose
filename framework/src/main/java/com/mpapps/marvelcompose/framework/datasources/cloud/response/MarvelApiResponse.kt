package com.mpapps.marvelcompose.framework.datasources.cloud.response

import kotlinx.serialization.Serializable

@Serializable
data class MarvelApiResponse(
    val code: Int,
    val data: DataResponse
)