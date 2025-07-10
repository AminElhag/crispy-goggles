package com.example.mobile_client_app.features.membership.main.data.remote


import com.example.mobile_client_app.features.membership.main.domain.model.CheckPromoCodeResponse
import com.example.mobile_client_app.features.membership.main.domain.model.MembershipResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface MembershipAPI {
    suspend fun getMembership(): Result<MembershipResponse, NetworkError>

    suspend fun checkPromoCode(promoCode: String): Result<CheckPromoCodeResponse, NetworkError>
}