package com.example.mobile_client_app.features.membership.main.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValidatePromoCodeRequest(
    @SerialName("promo_code")
    val promoCode: String,
    @SerialName("payment_plan_id")
    val paymentPlanId: Long,
)