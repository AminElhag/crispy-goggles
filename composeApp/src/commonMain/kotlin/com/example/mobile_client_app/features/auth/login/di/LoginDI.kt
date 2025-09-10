package com.example.mobile_client_app.features.auth.login.di

import com.example.mobile_client_app.features.auth.login.data.remote.api.LoginAPI
import com.example.mobile_client_app.features.auth.login.data.remote.api.LoginAPIImpl
import com.example.mobile_client_app.features.auth.login.domain.repository.UserRepository
import com.example.mobile_client_app.features.auth.login.domain.repository.UserRepositoryImpl
import com.example.mobile_client_app.features.auth.login.domain.usecase.LoginUseCase
import com.example.mobile_client_app.features.auth.login.presentation.ui.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginViewModel(get(),get()) }
    single { LoginUseCase(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<LoginAPI> { LoginAPIImpl(get()) }
}