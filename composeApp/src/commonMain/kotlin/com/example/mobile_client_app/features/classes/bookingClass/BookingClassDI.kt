package com.example.mobile_client_app.features.classes.bookingClass

import com.example.mobile_client_app.features.classes.bookingClass.domain.repository.ClassRepository
import com.example.mobile_client_app.features.classes.bookingClass.domain.repository.ClassRepositoryImpl
import com.example.mobile_client_app.features.classes.bookingClass.domain.usecase.GetClassDetailsUseCase
import com.example.mobile_client_app.features.classes.bookingClass.domain.usecase.SendBookingClassRequestUseCase
import com.example.mobile_client_app.features.classes.bookingClass.presentation.BookingClassViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val bookingClassModel = module {
    viewModel { BookingClassViewModel(get(), get()) }
    single { GetClassDetailsUseCase(get()) }
    single { SendBookingClassRequestUseCase(get()) }
    single<ClassRepository> { ClassRepositoryImpl(get()) }
}