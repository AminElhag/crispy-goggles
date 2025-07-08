package com.example.mobile_client_app.membership.main.domain.usercase

import com.example.mobile_client_app.membership.main.domain.model.MembershipResponse
import com.example.mobile_client_app.membership.main.domain.repository.MembershipRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class GetMembershipUserCase(
    private val membershipRepository: MembershipRepository
) {
    suspend operator fun invoke(token: String) : Result<MembershipResponse, NetworkError> {
        return membershipRepository.getMembership(token)
    }
}