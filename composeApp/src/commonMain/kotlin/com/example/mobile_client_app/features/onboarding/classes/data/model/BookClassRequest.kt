package com.example.mobile_client_app.features.onboarding.classes.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookClassRequest(
    @SerialName("class_id")
    val classId: Long
)