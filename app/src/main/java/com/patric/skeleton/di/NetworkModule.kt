package com.patric.skeleton.di

import co.touchlab.kermit.BuildConfig
import co.touchlab.kermit.Kermit
import com.patric.core.domain.error.CustomError
import com.patric.core.settings.SettingsApp
import com.patric.core.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.kotlinx.serializer.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import io.ktor.util.date.*
import io.ktor.utils.io.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun kermit(): Kermit = Kermit()

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun createJson() = Json {
        encodeDefaults = false
        isLenient = true
        ignoreUnknownKeys = true
        useArrayPolymorphism = true
        explicitNulls = false
    }

    @Provides
    @Singleton
    fun buildHttpClient(
        createJson: Json,
        log: Kermit
    ): HttpClient = HttpClient(Android) {
        expectSuccess = true
        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, _ ->
                controlException(exception)
            }
        }
        defaultRequest {
            host = Constants.BASE_URL
            url {
                protocol = URLProtocol.HTTPS
            }
        }
        install(ContentNegotiation) {
            json(createJson)
        }
        if (BuildConfig.DEBUG) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        log.v("Ktor") { message }
                    }
                }
                level = LogLevel.ALL
            }
        }
    }

    private suspend fun controlException(exception: Throwable) {
        try {
            val clientException = exception as ClientRequestException
            val exceptionResponse = clientException.response
            val status = exceptionResponse.status
            val msg = exceptionResponse.bodyAsText()
            when (exceptionResponse.status) {
                HttpStatusCode.NotFound -> throw CustomError.Repository.Http(
                    status.value,
                    msg
                )
                HttpStatusCode.BadRequest -> throw CustomError.Repository.BadRequest(
                    exceptionResponse.bodyAsText()
                )
                else -> throw CustomError.Generic(exceptionResponse.bodyAsText())
            }
        } catch (e: Exception) {
            throw CustomError.Generic("Ha habido un error, comprueba tu conexión a internet e internarlo más tarde")
        }
    }
}