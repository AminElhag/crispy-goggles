package com.example.backend.mobileClient.features.appointment.controller.models

import com.example.mobile_client_app.features.personalTraining.appointments.data.models.TrainerResponse
import com.example.mobile_client_app.features.personalTraining.appointments.domain.model.Appointment
import com.example.mobile_client_app.features.personalTraining.appointments.domain.model.Appointment.AppointmentStatus.Companion.getById
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppointmentResponse(
    @SerialName("id")
    val id: String,
    @SerialName("date")
    val date: String,
    @SerialName("time_range")
    val timeRange: String,
    @SerialName("trainer")
    val trainerResponse: TrainerResponse,
    @SerialName("status")
    val status: Int = 0
) {
    fun toDto() = Appointment(
        id, date, timeRange, trainerResponse.toDto(), getById(status) ?: Appointment.AppointmentStatus.CANCELLED
    )


}
