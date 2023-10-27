package com.mpapps.marvelcompose.framework.infrastructure.di

import com.mpapps.marvelcompose.framework.BuildConfig
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

@Module
@InstallIn(SingletonComponent::class)
class FrameworkModule {
    @Provides
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
}