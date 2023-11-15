package com.mpapps.marvelcompose.framework.datasources.cloud.response

import kotlinx.serialization.Serializable

@Serializable
data class Thumbnail (
    val path: String,
    val extension: String
)