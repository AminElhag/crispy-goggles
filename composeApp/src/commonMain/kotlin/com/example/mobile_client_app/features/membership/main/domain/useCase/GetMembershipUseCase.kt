package com.example.mobile_client_app.features.membership.main.domain.useCase

import com.example.mobile_client_app.features.membership.main.domain.model.MembershipResponse
import com.example.mobile_client_app.features.membership.main.domain.repository.MembershipRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class GetMembershipUseCase(
    private val membershipRepository: MembershipRepository
) {
    suspend operator fun invoke() : Result<MembershipResponse, NetworkError> {
        return membershipRepository.getMembership()
    }
}