package com.example.mobile_client_app.features.membership.main.domain.repository

import com.example.mobile_client_app.features.membership.main.domain.model.CheckPromoCodeResponse
import com.example.mobile_client_app.features.membership.main.domain.model.CheckoutInitResponse
import com.example.mobile_client_app.features.membership.main.domain.model.MembershipResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import kotlinx.datetime.LocalDate

interface MembershipRepository {
    suspend fun getMembership(): Result<MembershipResponse,NetworkError>
    suspend fun checkPromoCode(promoCode: String): Result<CheckPromoCodeResponse,NetworkError>
    suspend fun checkoutInit(
        membershipId: String,
        contractOptionId: String,
        startDate: LocalDate,
        promoCode: String?
    ): Result<CheckoutInitResponse, NetworkError>
}