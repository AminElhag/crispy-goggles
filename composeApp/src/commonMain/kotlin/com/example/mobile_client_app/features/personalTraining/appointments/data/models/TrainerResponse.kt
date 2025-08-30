package com.example.mobile_client_app.features.personalTraining.appointments.data.models

import com.example.mobile_client_app.features.personalTraining.appointments.domain.model.Trainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrainerResponse(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("avatar")
    val avatar: String,
    @SerialName("speciality")
    val specialty: String = "",
    @SerialName("rating")
    val rating: Double,
    @SerialName("review_count")
    val reviewCount: Int,
    @SerialName("discretion")
    val discretion: String,
    ) {
    fun toDto() = Trainer(
        id, name, avatar, specialty
    )
}