package com.example.mobile_client_app.features.auth.login.domain.usecase

import com.example.mobile_client_app.features.auth.login.domain.model.LoginResponse
import com.example.mobile_client_app.features.auth.login.domain.repository.UserRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class LoginUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(emailOrPhone: String, password: String): Result<LoginResponse, NetworkError> {
        return userRepository.login(emailOrPhone, password)
    }
}