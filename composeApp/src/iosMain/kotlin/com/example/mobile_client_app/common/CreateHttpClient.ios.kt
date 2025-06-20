package com.example.mobile_client_app.common

import com.example.mobile_client_app.di.provideHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual fun createHttpClientEngine(): HttpClientEngine {
    return Darwin.create()
}