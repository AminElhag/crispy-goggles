package com.example.mobile_client_app.features.onboarding.home.domain.usercase

import com.example.mobile_client_app.features.onboarding.home.data.remote.model.ClassResponse
import com.example.mobile_client_app.features.onboarding.home.domain.repository.HomeRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class GetUpcomingClassesUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(): Result<List<ClassResponse>, NetworkError> {
        return repository.getUpcomingClasses()
    }
}