package com.example.mobile_client_app.features.membership.payment.data.remote

import com.example.mobile_client_app.features.membership.payment.data.model.PaymentRequest
import com.example.mobile_client_app.features.membership.payment.data.model.PaymentResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface PaymentAPI {
    suspend fun requestPayment(paymentRequest: PaymentRequest): Result<PaymentResponse, NetworkError>
}
