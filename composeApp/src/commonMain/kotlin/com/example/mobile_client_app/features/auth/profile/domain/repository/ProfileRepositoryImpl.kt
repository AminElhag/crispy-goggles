package com.example.mobile_client_app.features.auth.profile.domain.repository

import com.example.mobile_client_app.features.auth.profile.data.remote.ProfileAPI
import com.example.mobile_client_app.features.auth.profile.data.remote.models.ProfileResponse
import com.example.mobile_client_app.features.auth.profile.domain.models.DateRange
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class ProfileRepositoryImpl(
    private val api: ProfileAPI
) : ProfileRepository {
    override suspend fun getProfile(): Result<ProfileResponse, NetworkError> {
        return api.getProfile()
    }

    override suspend fun sendFreezingRequest(dateRange: DateRange): Result<Unit, NetworkError> {
        return api.sendFreezingRequest(dateRange.toRequest())
    }
}