package com.example.mobile_client_app.features.membership.payment.domain.usecase

import com.example.mobile_client_app.features.membership.payment.data.model.PaymentResponse
import com.example.mobile_client_app.features.membership.payment.domain.repository.PaymentRepository
import com.example.mobile_client_app.features.membership.payment.domain.repository.dto.PaymentDto
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class RequestPaymentUseCase(
    private val repository: PaymentRepository
) {
    suspend fun invoke(contractId: Long, cardNumber: String, expirationDate: String, cardCVV: String): Result<PaymentResponse, NetworkError> {
        return repository.requestPayment(
            paymentDto = PaymentDto(
                contractId, cardNumber, expirationDate, cardCVV
            )
        )
    }
}
