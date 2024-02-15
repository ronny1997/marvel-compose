package com.mpapps.marvelcompose.framework.infrastructure.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.mpapps.marvelcompose.data.dataSource.MarvelDataSource
import com.mpapps.marvelcompose.data.dataSource.NumCallApiCacheDataSource
import com.mpapps.marvelcompose.framework.BuildConfig
import com.mpapps.marvelcompose.framework.datasources.cloud.MarvelDataSourceImpl
import com.mpapps.marvelcompose.framework.datasources.cloud.api.MarvelApi
import com.mpapps.marvelcompose.framework.datasources.cloud.api.MarvelApiImpl
import com.mpapps.marvelcompose.framework.datasources.local.cache.NumCallApiCacheDataSourceImpl
import com.mpapps.marvelcompose.framework.datasources.local.cache.service.DataStoreManager
import com.mpapps.marvelcompose.framework.datasources.local.cache.service.DataStoreManagerImpl
import com.mpapps.marvelcompose.framework.datasources.local.cache.service.MemoryCacheService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FrameworkModule {
    companion object {
        const val APP_CACHE = "cache"
    }

    @Provides
    @Singleton
    fun providerHttpClient(): HttpClient {
        return HttpClient(Android) {
            if (BuildConfig.DEBUG) {
                install(Logging) {
                    logger = Logger.ANDROID
                    level = LogLevel.BODY
                }
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        encodeDefaults = true
                        isLenient = true
                        allowSpecialFloatingPointValues = true
                        allowStructuredMapKeys = true
                        prettyPrint = true
                    }
                )
            }
            engine {
                connectTimeout = 200_000
            }
            defaultRequest {
                url("https://gateway.marvel.com/")
                contentType(ContentType.Application.Json)
            }
        }
    }

    @Provides
    @Singleton
    fun provideMemoryCacheService(dataStore: DataStoreManager): MemoryCacheService =
        MemoryCacheService(dataStore)

    @Provides
    @Singleton
    fun provideCacheManager(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            appContext.preferencesDataStoreFile(APP_CACHE)
        }
    }
}