package com.mpapps.marvelcompose.data.dataSource

import android.graphics.Bitmap

interface ImageDataSource {

    suspend fun getImageUrl(url: String): Bitmap?

}