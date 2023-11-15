package com.mpapps.marvelcompose.framework.datasources.cloud.response

import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse (
    val id: String,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
)