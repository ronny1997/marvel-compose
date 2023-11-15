package com.mpapps.marvelcompose.data.dataSource

interface NumCallApiCacheDataSource {
    suspend fun getNumCallApi(): Int?

    suspend fun setNumCallApi(num: Int)
}