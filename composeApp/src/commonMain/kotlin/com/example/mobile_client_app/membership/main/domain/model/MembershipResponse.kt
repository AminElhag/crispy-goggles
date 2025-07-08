package com.example.mobile_client_app.membership.main.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MembershipResponse(
    @SerialName("plans")
    val plans: List<PlanResponse>,
)
