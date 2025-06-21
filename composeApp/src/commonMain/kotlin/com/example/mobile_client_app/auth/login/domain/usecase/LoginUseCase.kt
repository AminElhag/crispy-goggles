package com.example.mobile_client_app.auth.login.domain.usecase

import com.example.mobile_client_app.auth.login.domain.model.LoginResponse
import com.example.mobile_client_app.auth.login.domain.repository.UserRepository
import com.example.mobile_client_app.util.NetworkError
import com.example.mobile_client_app.util.Result
import io.ktor.client.statement.HttpResponse

class LoginUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(emailOrPhone: String, password: String): Result<LoginResponse, NetworkError> {
        return userRepository.login(emailOrPhone, password)
    }
}