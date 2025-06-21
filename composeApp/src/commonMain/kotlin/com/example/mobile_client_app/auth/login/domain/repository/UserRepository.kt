package com.example.mobile_client_app.auth.login.domain.repository

import com.example.mobile_client_app.auth.login.data.local.api.UserAPI
import com.example.mobile_client_app.auth.login.domain.model.LoginResponse
import com.example.mobile_client_app.util.NetworkError
import com.example.mobile_client_app.util.Result
import io.ktor.client.statement.HttpResponse

interface UserRepository {
    suspend fun login(emailOrPhone: String, password: String): Result<LoginResponse, NetworkError>
}

class UserRepositoryImpl(private val userAPI: UserAPI) : UserRepository {
    override suspend fun login(emailOrPhone: String, password: String): Result<LoginResponse, NetworkError> {
        return userAPI.login(emailOrPhone, password)
    }
}