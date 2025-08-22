package com.example.mobile_client_app.features.personalTraining.appointments

import com.example.mobile_client_app.features.personalTraining.appointments.domain.repository.AppointmentRepository
import com.example.mobile_client_app.features.personalTraining.appointments.domain.repository.impl.AppointmentRepositoryImpl
import com.example.mobile_client_app.features.personalTraining.appointments.domain.usecase.GetAppointmentUseCase
import com.example.mobile_client_app.features.personalTraining.appointments.data.remote.AppointmentApi
import com.example.mobile_client_app.features.personalTraining.appointments.data.remote.impl.AppointmentApiImpl
import com.example.mobile_client_app.features.personalTraining.appointments.domain.usecase.CancelAppointmentUseCase
import com.example.mobile_client_app.features.personalTraining.appointments.presntation.AppointmentsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appointmentsModule = module {
    viewModel { AppointmentsViewModel(get(),get()) }
    single { GetAppointmentUseCase(get()) }
    single { CancelAppointmentUseCase(get()) }
    single<AppointmentRepository> { AppointmentRepositoryImpl(get()) }
    single<AppointmentApi> { AppointmentApiImpl(get()) }
}