package com.example.mobile_client_app.features.onboarding.di

import com.example.mobile_client_app.features.onboarding.home.data.remote.HomeAPI
import com.example.mobile_client_app.features.onboarding.home.data.remote.impl.HomeAPIImpl
import com.example.mobile_client_app.features.onboarding.home.domain.repository.HomeRepository
import com.example.mobile_client_app.features.onboarding.home.domain.repository.HomeRepositoryImpl
import com.example.mobile_client_app.features.onboarding.home.domain.usercase.GetBannersUseCase
import com.example.mobile_client_app.features.onboarding.home.domain.usercase.GetNotificationCountUseCase
import com.example.mobile_client_app.features.onboarding.home.domain.usercase.GetUpcomingClassesUseCase
import com.example.mobile_client_app.features.onboarding.home.presntation.HomeScreenViewModel
import com.example.mobile_client_app.features.onboarding.qrCode.data.remote.QrCodeAPI
import com.example.mobile_client_app.features.onboarding.qrCode.data.remote.impl.QrCodeAPIImpl
import com.example.mobile_client_app.features.onboarding.qrCode.domain.repository.QrCodeRepository
import com.example.mobile_client_app.features.onboarding.qrCode.domain.repository.impl.QrCodeRepositoryImpl
import com.example.mobile_client_app.features.onboarding.qrCode.domain.usecase.GetQrCodeDataUseCase
import com.example.mobile_client_app.features.onboarding.qrCode.presntation.QrCodeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val onBoardingModule = module {
    viewModel { HomeScreenViewModel(get(), get(), get()) }
    single<HomeAPI> { HomeAPIImpl(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single { GetUpcomingClassesUseCase(get()) }
    single { GetBannersUseCase(get()) }
    single { GetNotificationCountUseCase(get()) }

    viewModel { QrCodeViewModel(get()) }
    single { GetQrCodeDataUseCase(get()) }
    single<QrCodeRepository> { QrCodeRepositoryImpl(get()) }
    single<QrCodeAPI> { QrCodeAPIImpl(get()) }
}