package com.example.mobile_client_app.features.personalTraining.trainerSelection.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvailableTimeResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("is_available")
    val isAvailable: Boolean,
)