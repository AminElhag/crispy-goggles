package com.example.mobile_client_app.features.membership.main.domain.repository

import com.example.mobile_client_app.features.membership.main.data.remote.MembershipAPI
import com.example.mobile_client_app.features.membership.main.domain.model.CheckPromoCodeResponse
import com.example.mobile_client_app.features.membership.main.domain.model.CheckoutInitResponse
import com.example.mobile_client_app.features.membership.main.domain.model.MembershipResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import kotlinx.datetime.LocalDate

class MembershipRepositoryImpl(
    private val membershipAPI: MembershipAPI
) : MembershipRepository {
    override suspend fun getMembership(): Result<MembershipResponse, NetworkError> {
        return membershipAPI.getMembership()
    }

    override suspend fun checkPromoCode(promoCode: String, paymentPlanID: Long): Result<CheckPromoCodeResponse, NetworkError> {
        return membershipAPI.checkPromoCode(promoCode,paymentPlanID)
    }

    override suspend fun checkoutInit(
        membershipId: String,
        contractOptionId: String,
        startDate: LocalDate,
        promoCode: String?
    ): Result<CheckoutInitResponse, NetworkError> {
        TODO("Not yet implemented")
    }
}