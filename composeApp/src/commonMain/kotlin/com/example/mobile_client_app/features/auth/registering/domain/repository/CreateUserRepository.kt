package com.example.mobile_client_app.features.auth.registering.domain.repository

import com.example.mobile_client_app.features.auth.registering.domain.model.CreateUserResponse
import com.example.mobile_client_app.features.auth.registering.domain.model.UserDTO
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface CreateUserRepository {
    suspend fun createUser(user: UserDTO): Result<CreateUserResponse, NetworkError>
}

