package com.example.mobile_client_app.features.membership.payment.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequest(
    @SerialName("contract_id")
    val contractId: Long,
    @SerialName("card_number")
    val cardNumber: String,
    @SerialName("expiration_date")
    val expirationDate: String,
    @SerialName("card_cvc")
    val cardCVV: String
)