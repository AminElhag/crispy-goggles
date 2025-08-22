package com.example.mobile_client_app.features.personalTraining.appointments.domain.repository

import com.example.backend.mobileClient.features.appointment.controller.models.AppointmentResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface AppointmentRepository {
    suspend fun getAppointments(): Result<List<AppointmentResponse>, NetworkError>
    suspend fun cancelAppointment(appointmentId: String): Result<Unit, NetworkError>
}
