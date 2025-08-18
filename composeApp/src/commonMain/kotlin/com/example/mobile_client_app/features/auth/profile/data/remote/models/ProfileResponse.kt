package com.example.mobile_client_app.features.auth.profile.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    @SerialName("name")
    val name: String,
    @SerialName("member_since")
    val memberSince: String,
    @SerialName("membership_status")
    val membershipStatus: Int,
    @SerialName("status_metrics")
    val statusMetrics: List<StatusMetricResponse>,
    @SerialName("health_metrics")
    val healthMetrics: List<HealthMetricResponse>,
    @SerialName("can_freeze_membership")
    val canFreezeMembership: Boolean = false,
)