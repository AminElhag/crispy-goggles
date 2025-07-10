package com.example.mobile_client_app.membership.main.domain.useCase

import com.example.mobile_client_app.membership.main.domain.model.CheckoutInitResponse
import com.example.mobile_client_app.membership.main.domain.repository.MembershipRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import kotlinx.datetime.LocalDate

class CheckoutInitUseCase(
    private val membershipRepository: MembershipRepository
) {
    suspend operator fun invoke(
        membershipId: String,
        contractOptionId: String,
        startDate: LocalDate,
        promoCode: String? = null,
    ): Result<CheckoutInitResponse, NetworkError> {
        return membershipRepository.checkoutInit(
            membershipId, contractOptionId, startDate, promoCode
        )
    }
}
