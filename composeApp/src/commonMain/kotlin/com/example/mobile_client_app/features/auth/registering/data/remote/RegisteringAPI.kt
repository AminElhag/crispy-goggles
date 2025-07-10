package com.example.mobile_client_app.features.auth.registering.data.remote

import com.example.mobile_client_app.features.auth.registering.domain.model.CreateUserResponse
import com.example.mobile_client_app.features.auth.registering.domain.model.UserRequest
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface RegisteringAPI {
    suspend fun registerUser(
        userRequest: UserRequest
    ): Result<CreateUserResponse, NetworkError>
}