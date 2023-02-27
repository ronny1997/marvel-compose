package com.patric.data.remote.datasource.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LoginRequest(
    val tenant: String = "CORE",
    val username: String = "AN02",
    val password: String = "AN02",
    val device: String = "AN02",
    @SerialName("grant_type") val grantType: String = "password",
)