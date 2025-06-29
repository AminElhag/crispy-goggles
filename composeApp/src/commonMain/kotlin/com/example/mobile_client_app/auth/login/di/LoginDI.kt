package com.example.mobile_client_app.auth.login.di

import com.example.mobile_client_app.auth.login.data.local.api.LoginAPI
import com.example.mobile_client_app.auth.login.data.local.api.LoginAPIImpl
import com.example.mobile_client_app.auth.login.domain.repository.UserRepository
import com.example.mobile_client_app.auth.login.domain.repository.UserRepositoryImpl
import com.example.mobile_client_app.auth.login.domain.usecase.LoginUseCase
import com.example.mobile_client_app.auth.login.presentation.ui.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginViewModel(get()) }
    single { LoginUseCase(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<LoginAPI> { LoginAPIImpl(get()) }
}