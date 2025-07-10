package com.example.mobile_client_app.features.membership.main.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckoutInitResponse(
    @SerialName("payment_session_id")
    val paymentSessionId: String,
    @SerialName("next_action")
    val nextAction: String,
    @SerialName("payment_url")
    val paymentUrl: String,
    @SerialName("amount_due")
    val amountDue: Double,
    @SerialName("currency")
    val currency: String,
)
