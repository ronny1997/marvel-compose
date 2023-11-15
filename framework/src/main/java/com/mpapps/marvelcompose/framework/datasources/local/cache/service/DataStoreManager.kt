package com.mpapps.marvelcompose.framework.datasources.local.cache.service

interface DataStoreManager {
    suspend fun <T> get(key: String, clazz: Class<T>): T?
    suspend fun <T> set(key: String, value: T, clazz: Class<T>)
    fun clearAll()
}
