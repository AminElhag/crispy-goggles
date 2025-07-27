package com.example.mobile_client_app.features.onboarding.di

import com.example.mobile_client_app.features.onboarding.home.data.remote.HomeAPI
import com.example.mobile_client_app.features.onboarding.home.data.remote.impl.HomeAPIImpl
import com.example.mobile_client_app.features.onboarding.home.domain.repository.HomeRepository
import com.example.mobile_client_app.features.onboarding.home.domain.repository.HomeRepositoryImpl
import com.example.mobile_client_app.features.onboarding.home.domain.usercase.GetBannersUseCase
import com.example.mobile_client_app.features.onboarding.home.domain.usercase.GetNotificationCountUseCase
import com.example.mobile_client_app.features.onboarding.home.domain.usercase.GetUpcomingClassesUseCase
import com.example.mobile_client_app.features.onboarding.home.presntation.HomeScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val onBoardingModule = module {
    single<HomeAPI> { HomeAPIImpl(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single { GetUpcomingClassesUseCase(get()) }
    single { GetBannersUseCase(get()) }
    single { GetNotificationCountUseCase(get()) }
    viewModel { HomeScreenViewModel(get(), get(), get()) }
}