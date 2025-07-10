package com.example.mobile_client_app.membership.main.domain.useCase

import com.example.mobile_client_app.membership.main.domain.model.CheckPromoCodeResponse
import com.example.mobile_client_app.membership.main.domain.repository.MembershipRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class CheckPromoCodeUseCase(
    private val membershipRepository: MembershipRepository
) {
    suspend operator fun invoke(promoCode: String): Result<CheckPromoCodeResponse, NetworkError> {
        return membershipRepository.checkPromoCode(promoCode)
    }
}