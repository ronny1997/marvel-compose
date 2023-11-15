package com.mpapps.marvelcompose.framework.datasources.cloud.response

import kotlinx.serialization.Serializable

@Serializable
data class DataResponse (
    val results: List<CharacterResponse>
)