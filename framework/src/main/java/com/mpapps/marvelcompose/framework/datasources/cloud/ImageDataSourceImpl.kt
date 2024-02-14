package com.mpapps.marvelcompose.framework.datasources.cloud

import android.graphics.Bitmap
import com.mpapps.marvelcompose.data.dataSource.ImageDataSource
import com.mpapps.marvelcompose.framework.datasources.cloud.api.ImageApi
import javax.inject.Inject

class ImageDataSourceImpl @Inject constructor(
    private val imageApi: ImageApi
) : ImageDataSource {
    override suspend fun getImageUrl(url: String): Bitmap? {
        return imageApi.getImageUrl(url)
    }

}