package com.example.mobile_client_app.features.personalTraining.appointments.domain.usecase

import com.example.mobile_client_app.features.personalTraining.appointments.domain.repository.AppointmentRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class CancelAppointmentUseCase(
    private val repository: AppointmentRepository
) {
    suspend operator fun invoke(
        appointmentId: String,
    ): Result<Unit, NetworkError> {
        return repository.cancelAppointment(appointmentId)
    }
}
