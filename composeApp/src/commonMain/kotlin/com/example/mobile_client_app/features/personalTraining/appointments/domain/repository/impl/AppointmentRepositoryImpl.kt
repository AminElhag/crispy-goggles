package com.example.mobile_client_app.features.personalTraining.appointments.domain.repository.impl

import com.example.backend.mobileClient.features.appointment.controller.models.AppointmentResponse
import com.example.mobile_client_app.features.personalTraining.appointments.data.models.CancelAppointmentRequest
import com.example.mobile_client_app.features.personalTraining.appointments.domain.repository.AppointmentRepository
import com.example.mobile_client_app.features.personalTraining.appointments.domain.model.Appointment
import com.example.mobile_client_app.features.personalTraining.appointments.data.remote.AppointmentApi
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class AppointmentRepositoryImpl(
    private val api: AppointmentApi
) : AppointmentRepository {
    override suspend fun getAppointments(): Result<List<AppointmentResponse>, NetworkError> {
        return api.getAppointments()
    }

    override suspend fun cancelAppointment(appointmentId: String): Result<Unit, NetworkError> {
        return api.cancelAppointment(CancelAppointmentRequest(appointmentId))
    }
}