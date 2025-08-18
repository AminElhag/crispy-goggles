package com.example.mobile_client_app.features.auth.profile.domain.useCase

import com.example.mobile_client_app.features.auth.profile.domain.models.DateRange
import com.example.mobile_client_app.features.auth.profile.domain.repository.ProfileRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class SendFreezingRequestUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(
        dateRange: DateRange
    ): Result<Unit, NetworkError> {
        return profileRepository.sendFreezingRequest(dateRange)
    }
}
