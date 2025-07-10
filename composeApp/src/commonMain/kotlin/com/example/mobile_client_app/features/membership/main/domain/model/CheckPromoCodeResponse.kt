package com.example.mobile_client_app.features.membership.main.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckPromoCodeResponse(
    @SerialName("is_valid") val isValid: Boolean,
)
