package com.mpapps.marvelcompose.framework.datasources.local.cache

import com.mpapps.marvelcompose.framework.datasources.local.cache.NumCallApiCacheDataSourceImpl.Companion.NUM_CALL_API
import com.mpapps.marvelcompose.framework.datasources.local.cache.service.DataStoreManagerImpl
import com.mpapps.marvelcompose.framework.util.CoroutineUnitTest
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock

class NumCallApiCacheDataSourceImplTest : CoroutineUnitTest() {

    @InjectMocks
    private lateinit var numCallApiCacheDataSourceImpl: NumCallApiCacheDataSourceImpl

    @Mock
    private lateinit var dataStoreManager: DataStoreManagerImpl

    @Test
    fun onGet_givenExistingKey_shouldReturnObject() =  runBlocking {
            whenever(dataStoreManager.get<Int>(NUM_CALL_API)).doReturn(1)

            val result = numCallApiCacheDataSourceImpl.getNumCallApi()
            Assert.assertEquals(result, 1)
        }



}