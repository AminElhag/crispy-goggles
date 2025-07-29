package com.example.mobile_client_app.features.notifications.di

import com.example.mobile_client_app.features.notifications.data.remote.NotificationsAPI
import com.example.mobile_client_app.features.notifications.data.remote.NotificationsAPIImpl
import com.example.mobile_client_app.features.notifications.domain.repository.NotificationsRepository
import com.example.mobile_client_app.features.notifications.domain.repository.NotificationsRepositoryImpl
import com.example.mobile_client_app.features.notifications.domain.usecase.GetNotificationUseCase
import com.example.mobile_client_app.features.notifications.domain.usecase.GetNotificationsUseCase
import com.example.mobile_client_app.features.notifications.presntation.list.NotificationsListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val notificationsModule = module {
    viewModel { NotificationsListViewModel(get()) }
    single { GetNotificationsUseCase(get()) }
    single { GetNotificationUseCase(get()) }
    single<NotificationsRepository> { NotificationsRepositoryImpl(get()) }
    single<NotificationsAPI> { NotificationsAPIImpl(get()) }
}