package com.example.mobile_client_app.auth.login.domain.usecase

import com.example.mobile_client_app.auth.login.domain.repository.UserRepository
import io.ktor.client.statement.HttpResponse

class LoginUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(emailOrPhone: String, password: String): Boolean {
        return userRepository.login(emailOrPhone, password)
    }
}