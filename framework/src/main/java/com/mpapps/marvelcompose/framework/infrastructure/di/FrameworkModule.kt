package com.mpapps.marvelcompose.framework.infrastructure.di

import com.mpapps.marvelcompose.data.remote.MarvelDataSource
import com.mpapps.marvelcompose.framework.BuildConfig
import com.mpapps.marvelcompose.framework.datasources.cloud.MarvelApi
import com.mpapps.marvelcompose.framework.datasources.cloud.MarvelDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FrameworkModule {
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
    fun provideMarvelApi(httpClient: HttpClient): MarvelApi = MarvelApi(httpClient)

    @Provides
    @Singleton
    fun provideMarvelDataSource(api: MarvelApi): MarvelDataSource = MarvelDataSourceImpl(api)
}