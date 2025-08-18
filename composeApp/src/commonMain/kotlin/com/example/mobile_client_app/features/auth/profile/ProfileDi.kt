package com.example.mobile_client_app.features.auth.profile

import com.example.mobile_client_app.features.auth.profile.data.remote.ProfileAPI
import com.example.mobile_client_app.features.auth.profile.data.remote.ProfileAPIImpl
import com.example.mobile_client_app.features.auth.profile.domain.repository.ProfileRepository
import com.example.mobile_client_app.features.auth.profile.domain.repository.ProfileRepositoryImpl
import com.example.mobile_client_app.features.auth.profile.domain.useCase.GetProfileUseCase
import com.example.mobile_client_app.features.auth.profile.domain.useCase.SendFreezingRequestUseCase
import com.example.mobile_client_app.features.auth.profile.presentation.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    viewModel { ProfileViewModel(get(), get(),get()) }
    single { GetProfileUseCase(get()) }
    single { SendFreezingRequestUseCase(get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
    single<ProfileAPI> { ProfileAPIImpl(get()) }
}