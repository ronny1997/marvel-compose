package com.patric.data.remote.datasource.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeneratedPageResponseTest(
    @SerialName("test")
    val test: String
)