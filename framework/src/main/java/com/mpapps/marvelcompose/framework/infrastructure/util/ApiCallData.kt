package com.mpapps.marvelcompose.framework.infrastructure.util

import java.math.BigInteger
import java.security.MessageDigest
import java.util.Date

data class ApiCallData(
    val milliseconds: String = Date().time.toString(),
    val apiKey: String = "40c0f1172b2c2c7fc17e73d2d7a8b016",
    val apiPrivateKey: String = "5a1a40a16c897303947bdef0a32e2302b75c4105",
) {

    fun calculateMD5Hash(): String {
        val combinedString = milliseconds + apiPrivateKey + apiKey
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(combinedString.toByteArray())).toString(16).padStart(32, '0')
    }

}