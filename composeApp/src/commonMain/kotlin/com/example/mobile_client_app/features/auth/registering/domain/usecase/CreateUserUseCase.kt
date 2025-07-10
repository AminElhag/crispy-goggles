package com.example.mobile_client_app.features.auth.registering.domain.usecase

import com.example.mobile_client_app.features.auth.registering.domain.model.CreateUserResponse
import com.example.mobile_client_app.features.auth.registering.domain.model.UserDTO
import com.example.mobile_client_app.features.auth.registering.domain.repository.CreateUserRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class CreateUserUseCase(private val createUserRepository: CreateUserRepository) {
    suspend operator fun invoke(user: UserDTO): Result<CreateUserResponse, NetworkError> {
        return createUserRepository.createUser(user = user)
    }
}