package com.example.mobile_client_app.features.onboarding.home.domain.usercase

import com.example.mobile_client_app.features.onboarding.home.domain.repository.HomeRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class GetNotificationCountUseCase(
    private val repository: HomeRepository,
) {
    suspend operator fun invoke(): Result<Int, NetworkError> {
        return repository.getNotificationCount()
    }
}