package com.example.mobile_client_app.features.personalTraining.trainerSelection.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrainingSpecializationResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("is_available")
    val isAvailable: Boolean
) {}
