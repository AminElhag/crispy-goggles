package com.example.mobile_client_app.features.auth.login.data.remote.api

import com.example.mobile_client_app.features.auth.login.domain.model.LoginResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface LoginAPI {
    suspend fun login(
        emailOrPhone: String,
        password: String
    ): Result<LoginResponse, NetworkError>
}

