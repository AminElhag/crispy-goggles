package com.example.mobile_client_app.features.classes.bookingClass.data.models

import com.example.mobile_client_app.features.personalTraining.appointments.data.models.TrainerResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClassDetailsResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("img_url")
    val imgUrl: String,
    @SerialName("duration")
    val duration: String,
    @SerialName("intensity")
    val intensity: List<String>,
    @SerialName("equipment")
    val equipment: List<String>?,
    @SerialName("trainer")
    val trainer: TrainerResponse
) {

}
