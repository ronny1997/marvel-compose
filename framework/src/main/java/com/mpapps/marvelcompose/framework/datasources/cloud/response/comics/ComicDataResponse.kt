package com.mpapps.marvelcompose.framework.datasources.cloud.response.comics

import kotlinx.serialization.Serializable

@Serializable
data class ComicDataResponse(
    val results: List<ComicResponse>
)