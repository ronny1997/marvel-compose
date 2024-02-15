package com.mpapps.marvelcompose.framework.infrastructure.di

import com.mpapps.marvelcompose.data.dataSource.MarvelDataSource
import com.mpapps.marvelcompose.data.dataSource.NumCallApiCacheDataSource
import com.mpapps.marvelcompose.framework.datasources.cloud.MarvelDataSourceImpl
import com.mpapps.marvelcompose.framework.datasources.cloud.api.MarvelApi
import com.mpapps.marvelcompose.framework.datasources.cloud.api.MarvelApiImpl
import com.mpapps.marvelcompose.framework.datasources.local.cache.NumCallApiCacheDataSourceImpl
import com.mpapps.marvelcompose.framework.datasources.local.cache.service.DataStoreManager
import com.mpapps.marvelcompose.framework.datasources.local.cache.service.DataStoreManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class FrameworksBindsModule {
    @Binds
    abstract fun provideDataStoreManager(dataStoreManagerImpl: DataStoreManagerImpl): DataStoreManager

    @Binds
    abstract fun provideNumCallApiCacheDataSource(numCallApiCacheDataSourceImpl: NumCallApiCacheDataSourceImpl): NumCallApiCacheDataSource

    @Binds
    abstract fun provideMarvelApi(marvelApiImpl: MarvelApiImpl): MarvelApi

    @Binds
    abstract fun provideMarvelDataSource(marvelDataSourceImpl: MarvelDataSourceImpl): MarvelDataSource

}
