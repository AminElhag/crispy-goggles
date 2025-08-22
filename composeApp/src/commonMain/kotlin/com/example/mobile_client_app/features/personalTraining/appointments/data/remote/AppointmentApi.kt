package com.example.mobile_client_app.features.personalTraining.appointments.data.remote

import com.example.backend.mobileClient.features.appointment.controller.models.AppointmentResponse
import com.example.mobile_client_app.features.personalTraining.appointments.data.models.CancelAppointmentRequest
import com.example.mobile_client_app.features.personalTraining.appointments.domain.model.Appointment
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface AppointmentApi {
    suspend fun getAppointments(): Result<List<AppointmentResponse>, NetworkError>
    suspend fun cancelAppointment(request: CancelAppointmentRequest): Result<Unit, NetworkError>
}
