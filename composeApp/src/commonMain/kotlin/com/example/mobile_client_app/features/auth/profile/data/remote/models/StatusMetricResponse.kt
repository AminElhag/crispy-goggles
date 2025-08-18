package com.example.mobile_client_app.features.auth.profile.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusMetricResponse(
    @SerialName("title")
    val title: String,
    @SerialName("value")
    val value: String,
    @SerialName("subtitle")
    val subtitle: String? = null
)
