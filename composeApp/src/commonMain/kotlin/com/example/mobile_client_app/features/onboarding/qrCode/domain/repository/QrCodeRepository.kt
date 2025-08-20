package com.example.mobile_client_app.features.onboarding.qrCode.domain.repository

import com.example.mobile_client_app.features.onboarding.qrCode.data.model.QrCodeDataResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface QrCodeRepository {
    suspend fun getQrCodeData(): Result<QrCodeDataResponse, NetworkError>
}
