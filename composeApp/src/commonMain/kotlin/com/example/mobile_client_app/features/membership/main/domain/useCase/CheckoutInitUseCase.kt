package com.example.mobile_client_app.features.membership.main.domain.useCase

import com.example.mobile_client_app.features.membership.main.domain.model.CheckoutInitRequest
import com.example.mobile_client_app.features.membership.main.domain.model.CheckoutInitResponse
import com.example.mobile_client_app.features.membership.main.domain.repository.MembershipRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import kotlinx.datetime.LocalDate

class CheckoutInitUseCase(
    private val membershipRepository: MembershipRepository
) {
    suspend operator fun invoke(
        request: CheckoutInitRequest,
    ): Result<CheckoutInitResponse, NetworkError> {
        return membershipRepository.checkoutInit(request)
    }
}
