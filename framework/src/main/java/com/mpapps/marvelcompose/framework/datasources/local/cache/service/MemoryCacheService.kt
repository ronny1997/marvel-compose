package com.mpapps.marvelcompose.framework.datasources.local.cache.service

import javax.inject.Inject

 class MemoryCacheService @Inject constructor(
    private val dataStoreManager: DataStoreManager
) {
     fun clearAll() {
        dataStoreManager.clearAll()
    }
}