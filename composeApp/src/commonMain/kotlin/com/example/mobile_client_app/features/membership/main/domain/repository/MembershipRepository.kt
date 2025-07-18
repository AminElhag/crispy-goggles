package com.example.mobile_client_app.features.membership.main.domain.repository

import com.example.mobile_client_app.features.membership.main.domain.model.CheckPromoCodeResponse
import com.example.mobile_client_app.features.membership.main.domain.model.CheckoutInitRequest
import com.example.mobile_client_app.features.membership.main.domain.model.CheckoutInitResponse
import com.example.mobile_client_app.features.membership.main.domain.model.MembershipResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface MembershipRepository {
    suspend fun getMembership(): Result<MembershipResponse, NetworkError>
    suspend fun checkPromoCode(promoCode: String, paymentPlanID: Long): Result<CheckPromoCodeResponse, NetworkError>
    suspend fun checkoutInit(request: CheckoutInitRequest): Result<CheckoutInitResponse, NetworkError>
}