package com.example.mobile_client_app.features.personalTraining.appointments.domain.model

import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.cancel
import mobile_client_app.composeapp.generated.resources.complete
import mobile_client_app.composeapp.generated.resources.in_progress
import mobile_client_app.composeapp.generated.resources.scheduled
import org.jetbrains.compose.resources.StringResource

data class Appointment(
    val id: String,
    val date: String,
    val timeRange: String,
    val trainer: Trainer,
    val status: AppointmentStatus = AppointmentStatus.SCHEDULED
) {
    enum class AppointmentStatus(val id: Int,val displayName: StringResource) {
        SCHEDULED(0, Res.string.scheduled),
        IN_PROGRESS(1,Res.string.in_progress),
        COMPLETED(2, Res.string.complete),
        CANCELLED(3, Res.string.cancel);

        companion object {
            fun getById(id: Int): AppointmentStatus? = entries.find { it.id == id }
        }
    }

}