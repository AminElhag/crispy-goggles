package com.example.mobile_client_app.features.auth.profile.data.remote

import com.example.mobile_client_app.features.auth.profile.data.remote.models.FreezingRequest
import com.example.mobile_client_app.features.auth.profile.data.remote.models.ProfileResponse
import com.example.mobile_client_app.features.auth.profile.domain.models.DateRange
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface ProfileAPI {
    suspend fun getProfile() : Result<ProfileResponse, NetworkError>
    suspend fun sendFreezingRequest(freezingRequest: FreezingRequest): Result<Unit, NetworkError>
}