package com.example.mobile_client_app.auth.login.domain.repository

import com.example.mobile_client_app.auth.login.data.local.api.UserAPI
import io.ktor.client.statement.HttpResponse

interface UserRepository {
    suspend fun login(emailOrPhone: String, password: String): Boolean
}

class UserRepositoryImpl(private val userAPI: UserAPI) : UserRepository {
    override suspend fun login(emailOrPhone: String, password: String): Boolean {
        return userAPI.login(emailOrPhone, password)
    }
}