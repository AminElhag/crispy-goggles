package com.example.mobile_client_app.features.auth.profile.domain.useCase

import com.example.mobile_client_app.features.auth.profile.data.remote.models.ProfileResponse
import com.example.mobile_client_app.features.auth.profile.domain.repository.ProfileRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class GetProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(): Result<ProfileResponse, NetworkError> {
        return repository.getProfile()
    }
}