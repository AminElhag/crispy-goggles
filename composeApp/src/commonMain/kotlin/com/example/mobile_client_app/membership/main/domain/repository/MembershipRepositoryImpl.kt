package com.example.mobile_client_app.membership.main.domain.repository

import com.example.mobile_client_app.membership.main.data.remote.MembershipAPI
import com.example.mobile_client_app.membership.main.domain.model.MembershipResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class MembershipRepositoryImpl(
    private val membershipAPI: MembershipAPI
) : MembershipRepository {
    override suspend fun getMembership(token: String): Result<MembershipResponse, NetworkError> {
        return membershipAPI.getMembership(token)
    }
}