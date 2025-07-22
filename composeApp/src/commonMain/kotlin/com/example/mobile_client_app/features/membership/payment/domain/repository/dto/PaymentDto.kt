package com.example.mobile_client_app.features.membership.payment.domain.repository.dto

import com.example.mobile_client_app.features.membership.payment.data.model.PaymentRequest

data class PaymentDto(
    val contractId: Long,
    val cardNumber: String,
    val expirationDate: String,
    val cardCVV: String
) {
    fun toRequest() = PaymentRequest(
        contractId = contractId,
        cardNumber = cardNumber,
        expirationDate = expirationDate,
        cardCVV = cardCVV
    )
}