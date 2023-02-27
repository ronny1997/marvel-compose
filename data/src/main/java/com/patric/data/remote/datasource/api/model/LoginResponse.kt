package com.patric.data.remote.datasource.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse constructor(
    @SerialName("access_token") val token: String,
    val version: String,
    @SerialName(".expires") val expirationDate: String,
    val deviceMenuCode: String,
    val culture: String
)