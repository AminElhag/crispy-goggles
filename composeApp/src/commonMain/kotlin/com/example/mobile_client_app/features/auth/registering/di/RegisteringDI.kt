package com.example.mobile_client_app.features.auth.registering.di

import com.example.mobile_client_app.features.auth.registering.data.remote.RegisteringAPI
import com.example.mobile_client_app.features.auth.registering.data.remote.RegisteringAPIImpl
import com.example.mobile_client_app.features.auth.registering.domain.repository.CreateUserRepository
import com.example.mobile_client_app.features.auth.registering.domain.repository.CreateUserRepositoryImpl
import com.example.mobile_client_app.features.auth.registering.domain.usecase.CreateUserUseCase
import com.example.mobile_client_app.features.auth.registering.presentaion.ui.RegisteringViewModel
import org.koin.dsl.module

val registeringModule = module {
    single { RegisteringViewModel(get(),get()) }
    single { CreateUserUseCase(get()) }
    single<CreateUserRepository> { CreateUserRepositoryImpl(get()) }
    single<RegisteringAPI> { RegisteringAPIImpl(get()) }
}