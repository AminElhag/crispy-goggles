package com.example.mobile_client_app.membership.main.domain.repository

import com.example.mobile_client_app.membership.main.domain.model.MembershipResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface MembershipRepository {
    suspend fun getMembership(token: String): Result<MembershipResponse, NetworkError>
}