package com.example.mobile_client_app.features.auth.profile.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HealthMetricResponse(
    @SerialName("title")
    val title: String,
    @SerialName("subtitle")
    val subtitle: String,
    @SerialName("value")
    val value: String,
    @SerialName("change")
    val change: String? = null,
    @SerialName("is_positive_change")
    val isPositiveChange: Boolean? = null,
    @SerialName("icon_id")
    val iconId: Int? = null,
)