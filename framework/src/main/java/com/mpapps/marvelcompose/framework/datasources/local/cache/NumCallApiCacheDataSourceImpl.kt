package com.mpapps.marvelcompose.framework.datasources.local.cache

import com.mpapps.marvelcompose.data.dataSource.NumCallApiCacheDataSource
import com.mpapps.marvelcompose.framework.datasources.local.cache.service.DataStoreManager
import javax.inject.Inject

internal class NumCallApiCacheDataSourceImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : NumCallApiCacheDataSource {
    override suspend fun getNumCallApi(): Int? =
        dataStoreManager.get(NUM_CALL_API, Int::class.java)

    override suspend fun setNumCallApi(num: Int) =
        dataStoreManager.set(NUM_CALL_API, num, Int::class.java)

    companion object {
        const val NUM_CALL_API = "NUM_CALL_API"
    }
}