package com.example.mobile_client_app.features.onboarding.classes.domain.usecase

import com.example.mobile_client_app.features.onboarding.classes.data.model.FitnessClassResponse
import com.example.mobile_client_app.features.onboarding.classes.domain.repository.ClassesRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class GetClassesUseCase(
    private val repository: ClassesRepository
) {
    suspend operator fun invoke(): Result<List<FitnessClassResponse>, NetworkError> {
        return repository.getClasses()
    }
}
