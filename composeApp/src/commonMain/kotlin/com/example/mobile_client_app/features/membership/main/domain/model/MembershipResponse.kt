package com.example.mobile_client_app.features.membership.main.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MembershipResponse(
    @SerialName("membership_plans")
    val plans:List<MembershipPlan>
)
