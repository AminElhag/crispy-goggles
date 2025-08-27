package com.example.mobile_client_app.features.personalTraining.appointments.data.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestAppointmentRequest(
    @SerialName("trainer_id")
    val trainerId: String,
    @SerialName("selected_training_type")
    val selectedTrainingType: Int,
    @SerialName("selected_specialization_type")
    val selectedSpecializationType: Int,
    @SerialName("selected_date")
    val selectedDate: LocalDate,
    @SerialName("selected_time_id")
    val selectedTimeId: Int
)