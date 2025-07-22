package com.example.mobile_client_app.features.membership.payment.domain.repository

import com.example.mobile_client_app.features.membership.payment.data.model.PaymentResponse
import com.example.mobile_client_app.features.membership.payment.domain.repository.dto.PaymentDto
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface PaymentRepository {
    suspend fun requestPayment(paymentDto: PaymentDto): Result<PaymentResponse, NetworkError>
}
