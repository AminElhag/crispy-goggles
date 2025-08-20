package com.example.mobile_client_app.features.onboarding.qrCode.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QrCodeDataResponse(
    @SerialName("qr_code") val qrCode: String,
) {
    override fun toString(): String {
        return super.toString()
    }
}