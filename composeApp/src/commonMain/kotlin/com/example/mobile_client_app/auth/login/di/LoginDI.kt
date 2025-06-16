package com.example.mobile_client_app.auth.login.di

import com.example.mobile_client_app.auth.login.data.local.api.UserAPI
import com.example.mobile_client_app.auth.login.data.local.api.UserAPIImpl
import com.example.mobile_client_app.auth.login.domain.repository.UserRepository
import com.example.mobile_client_app.auth.login.domain.repository.UserRepositoryImpl
import com.example.mobile_client_app.auth.login.domain.usecase.LoginUseCase
import com.example.mobile_client_app.auth.login.presentation.ui.LoginScreenViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginScreenViewModel(get()) }
    single { LoginUseCase(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<UserAPI> { UserAPIImpl(get()) }
    single { HttpClient() }
}