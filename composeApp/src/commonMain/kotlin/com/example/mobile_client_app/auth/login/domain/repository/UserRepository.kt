package com.example.mobile_client_app.auth.login.domain.repository

import com.example.mobile_client_app.auth.login.data.local.api.LoginAPI
import com.example.mobile_client_app.auth.login.domain.model.LoginResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface UserRepository {
    suspend fun login(emailOrPhone: String, password: String): Result<LoginResponse, NetworkError>
}

class UserRepositoryImpl(private val userAPI: LoginAPI) : UserRepository {
    override suspend fun login(emailOrPhone: String, password: String): Result<LoginResponse, NetworkError> {
        return userAPI.login(emailOrPhone, password)
    }
}