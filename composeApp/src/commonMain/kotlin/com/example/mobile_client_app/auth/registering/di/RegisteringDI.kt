package com.example.mobile_client_app.auth.registering.di

import com.example.mobile_client_app.auth.registering.data.remote.RegisteringAPI
import com.example.mobile_client_app.auth.registering.data.remote.RegisteringAPIImpl
import com.example.mobile_client_app.auth.registering.domain.repository.CreateUserRepository
import com.example.mobile_client_app.auth.registering.domain.repository.CreateUserRepositoryImpl
import com.example.mobile_client_app.auth.registering.domain.usecase.CreateUserUseCase
import com.example.mobile_client_app.auth.registering.presentaion.ui.RegisteringViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val registeringModule = module {
    single { RegisteringViewModel(get()) }
    single { CreateUserUseCase(get()) }
    single<CreateUserRepository> { CreateUserRepositoryImpl(get()) }
    single<RegisteringAPI> { RegisteringAPIImpl(get()) }
}