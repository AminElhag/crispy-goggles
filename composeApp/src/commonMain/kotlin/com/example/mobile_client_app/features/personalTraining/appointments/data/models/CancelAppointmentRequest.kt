package com.example.mobile_client_app.features.personalTraining.appointments.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CancelAppointmentRequest(
    @SerialName("id")
    val appointmentId: String,
) {

}
