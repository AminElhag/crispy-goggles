package com.example.mobile_client_app.features.auth.registering.domain.repository

import com.example.mobile_client_app.features.auth.registering.data.remote.RegisteringAPI
import com.example.mobile_client_app.features.auth.registering.domain.model.CreateUserResponse
import com.example.mobile_client_app.features.auth.registering.domain.model.UserDTO
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class CreateUserRepositoryImpl(
    private val registeringAPI: RegisteringAPI
) : CreateUserRepository {
    override suspend fun createUser(user: UserDTO):Result<CreateUserResponse, NetworkError> {
        return registeringAPI.registerUser(user.toRequest())
    }
}