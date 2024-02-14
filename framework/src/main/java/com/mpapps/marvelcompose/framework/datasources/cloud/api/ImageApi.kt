package com.mpapps.marvelcompose.framework.datasources.cloud.api

import android.graphics.Bitmap

interface ImageApi {
    suspend fun getImageUrl(url: String): Bitmap?
}