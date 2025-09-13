package com.example.mobile_client_app.features.onboarding.qrCode.data.remote.impl

import com.example.mobile_client_app.features.onboarding.qrCode.data.model.QrCodeDataResponse
import com.example.mobile_client_app.features.onboarding.qrCode.data.remote.QrCodeAPI
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import com.example.mobile_client_app.util.network.convertToNetworkError
import com.example.mobile_client_app.util.network.toException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class QrCodeAPIImpl(
    private val httpClient: HttpClient
) : QrCodeAPI {
    override suspend fun getQrCodeData(): Result<QrCodeDataResponse, NetworkError> {
        val response = try {
            httpClient.get("/api/profile/qr-code")
        } catch (e: NetworkError) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(convertToNetworkError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<QrCodeDataResponse>()
                Result.Success(response)
            }
            else -> Result.Error(response.toException())
        }

    }
}