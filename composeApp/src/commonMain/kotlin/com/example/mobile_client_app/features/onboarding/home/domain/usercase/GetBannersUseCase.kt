package com.example.mobile_client_app.features.onboarding.home.domain.usercase

import com.example.mobile_client_app.features.onboarding.home.data.remote.model.BannerResponse
import com.example.mobile_client_app.features.onboarding.home.domain.repository.HomeRepository
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class GetBannersUseCase(
    private val repositoryImpl: HomeRepository
) {
    suspend operator fun invoke(): Result<List<BannerResponse>, NetworkError> {
        return repositoryImpl.getBanners()
    }
}