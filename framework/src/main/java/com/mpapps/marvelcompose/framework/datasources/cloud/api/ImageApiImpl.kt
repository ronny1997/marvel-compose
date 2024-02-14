package com.mpapps.marvelcompose.framework.datasources.cloud.api

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.mpapps.marvelcompose.framework.infrastructure.base.ApiBase
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import io.ktor.http.Url
import java.io.ByteArrayInputStream
import javax.inject.Inject

class ImageApiImpl @Inject constructor(private val client: HttpClient) : ApiBase(), ImageApi {
    override suspend fun getImageUrl(url: String): Bitmap? {
        val byteArray = client.get(Url(url)).readBytes()

        return ByteArrayInputStream(byteArray).use {
            val option = BitmapFactory.Options()
            option.inPreferredConfig =
                Bitmap.Config.RGB_565
            BitmapFactory.decodeStream(it, null, option)
        }
    }
}