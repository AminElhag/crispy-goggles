package com.example.mobile_client_app.di

import com.example.mobile_client_app.auth.login.data.local.api.UserAPIImpl
import com.example.mobile_client_app.auth.login.di.loginModule
import com.example.mobile_client_app.auth.login.domain.repository.UserRepositoryImpl
import com.example.mobile_client_app.auth.login.domain.usecase.LoginUseCase
import com.example.mobile_client_app.auth.login.presentation.ui.LoginScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            loginModule
        )
    }


fun provideHttpClient(): HttpClient {
    return HttpClient {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json.Default)
        }
    }
}