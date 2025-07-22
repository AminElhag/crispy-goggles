package com.example.mobile_client_app.features.membership.payment.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentResponse(
    @SerialName("payment_response_id")
    val id: Long,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String? = null,
    @SerialName("transaction_id")
    val transactionId: String?,
    @SerialName("processor_reference")
    val processorReference: String? = null,
    @SerialName("error_code")
    val errorCode: String? = null
)
