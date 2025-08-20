package com.example.mobile_client_app.features.onboarding.qrCode.domain.usecase

import com.example.mobile_client_app.features.onboarding.qrCode.data.model.QrCodeDataResponse
import com.example.mobile_client_app.features.onboarding.qrCode.domain.repository.QrCodeRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class GetQrCodeDataUseCase(
    private val repository: QrCodeRepository
) {
    suspend operator fun invoke() : Result<QrCodeDataResponse, NetworkError> {
        return repository.getQrCodeData()
    }
}