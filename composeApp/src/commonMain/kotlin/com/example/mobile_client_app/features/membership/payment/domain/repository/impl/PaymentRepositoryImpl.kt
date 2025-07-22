package com.example.mobile_client_app.features.membership.payment.domain.repository.impl

import com.example.mobile_client_app.features.membership.payment.data.model.PaymentResponse
import com.example.mobile_client_app.features.membership.payment.data.remote.PaymentAPI
import com.example.mobile_client_app.features.membership.payment.domain.repository.PaymentRepository
import com.example.mobile_client_app.features.membership.payment.domain.repository.dto.PaymentDto
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class PaymentRepositoryImpl(
    private val api: PaymentAPI
) : PaymentRepository {
    override suspend fun requestPayment(
        paymentDto: PaymentDto
    ): Result<PaymentResponse, NetworkError> {
        return api.requestPayment(paymentDto.toRequest())
    }
}