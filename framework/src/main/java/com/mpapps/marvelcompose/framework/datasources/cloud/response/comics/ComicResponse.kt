package com.mpapps.marvelcompose.framework.datasources.cloud.response.comics

import com.mpapps.marvelcompose.framework.datasources.cloud.response.Thumbnail
import kotlinx.serialization.Serializable

@Serializable
data class ComicResponse (
    val title: String,
    val thumbnail: Thumbnail,
)