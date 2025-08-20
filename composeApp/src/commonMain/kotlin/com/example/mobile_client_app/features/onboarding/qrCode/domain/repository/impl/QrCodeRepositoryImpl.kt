package com.example.mobile_client_app.features.onboarding.qrCode.domain.repository.impl

import com.example.mobile_client_app.features.onboarding.qrCode.data.model.QrCodeDataResponse
import com.example.mobile_client_app.features.onboarding.qrCode.data.remote.QrCodeAPI
import com.example.mobile_client_app.features.onboarding.qrCode.domain.repository.QrCodeRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class QrCodeRepositoryImpl(
    private val api: QrCodeAPI
) : QrCodeRepository {
    override suspend fun getQrCodeData(): Result<QrCodeDataResponse, NetworkError> {
        return api.getQrCodeData()
    }
}