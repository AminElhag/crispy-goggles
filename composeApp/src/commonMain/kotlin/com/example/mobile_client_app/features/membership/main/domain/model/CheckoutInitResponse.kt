package com.example.mobile_client_app.features.membership.main.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckoutInitResponse(
    @SerialName("total_amount_without_tax") val totalAmountWithoutTax: String,
    @SerialName("total_tax") val totalTax: String,
    @SerialName("total_amount") val totalAmount: String,
    @SerialName("contract_id") val contractId: Long
)
