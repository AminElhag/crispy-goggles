package com.example.mobile_client_app.features.personalTraining.appointments.domain.usecase

import com.example.backend.mobileClient.features.appointment.controller.models.AppointmentResponse
import com.example.mobile_client_app.features.personalTraining.appointments.domain.repository.AppointmentRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class GetAppointmentUseCase(
    private val repository: AppointmentRepository
) {
    suspend operator fun invoke(): Result<List<AppointmentResponse>, NetworkError> {
        return repository.getAppointments()
    }

}
