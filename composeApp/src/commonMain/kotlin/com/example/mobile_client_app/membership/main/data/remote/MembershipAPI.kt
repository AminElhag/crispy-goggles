package com.example.mobile_client_app.membership.main.data.remote

import com.example.mobile_client_app.membership.main.domain.model.MembershipResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface MembershipAPI {
    suspend fun getMembership(
        token: String,
    ): Result<MembershipResponse, NetworkError>
}