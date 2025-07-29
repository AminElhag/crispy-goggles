package com.example.mobile_client_app.features.notifications.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetNotificationDetailsResponse(
    @SerialName("id")
    val id: Long,
)
