package com.example.mobile_client_app.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.mobile_client_app.common.TokenManager
import com.example.mobile_client_app.common.createHttpClientEngine
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import saschpe.log4k.Log

val networkModule = module {
    single<HttpClient> { provideHttpClient(get(), get()) }
    single<HttpClientEngine> { createHttpClientEngine() }
    single { TokenManager(get<DataStore<Preferences>>()) }
}

fun provideHttpClient(
    engine: HttpClientEngine,
    tokenManager: TokenManager,
): HttpClient {
    return HttpClient(engine) {

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    if (message.contains("Authorization")) {
                        // Redact token in logs
                        Log.debug("HTTP ${message.replaceAfter("Bearer", "[REDACTED]")}")
                    } else {
                        Log.debug("HTTP $message")
                    }
                    println("Ktor: $message")
                }
            }
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                    explicitNulls = false
                })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
            socketTimeoutMillis = 30_000
        }
        install(Auth) {
            bearer {
                loadTokens {
                    tokenManager.getToken()?.let { BearerTokens(it, it) }
                }
            }
        }
        defaultRequest {
            host = "10.55.107.17"
            port = 8080
            contentType(ContentType.Application.Json)
        }
    }
}
