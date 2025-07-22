package com.example.mobile_client_app.features.membership.payment.data.remote

import com.example.mobile_client_app.features.membership.payment.data.model.PaymentRequest
import com.example.mobile_client_app.features.membership.payment.data.model.PaymentResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import com.example.mobile_client_app.util.network.toException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import saschpe.log4k.Log

class PaymentAPIImpl(
    private val httpClient: HttpClient
) : PaymentAPI {
    override suspend fun requestPayment(paymentRequest: PaymentRequest): Result<PaymentResponse, NetworkError> {
        val response = try {
            httpClient.post("/api/v1/contract/payment") {
                setBody(paymentRequest)
            }
        } catch (e: NetworkError) {
            Log.debug { "Network error :${e.displayMessage}" }
            return Result.Error(e)
        } catch (e: Exception) {
            Log.debug { "Error :${e}" }
            return Result.Error(NetworkError.UnknownError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<PaymentResponse>()
                Result.Success(response)
            }

            else -> Result.Error(response.toException())
        }
    }
}