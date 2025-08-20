package com.example.mobile_client_app.features.onboarding.qrCode.data.remote

import com.example.mobile_client_app.features.onboarding.qrCode.data.model.QrCodeDataResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface QrCodeAPI {
    suspend fun getQrCodeData(): Result<QrCodeDataResponse, NetworkError>
}
