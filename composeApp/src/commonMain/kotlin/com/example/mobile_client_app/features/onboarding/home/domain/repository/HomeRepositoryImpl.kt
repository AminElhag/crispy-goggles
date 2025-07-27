package com.example.mobile_client_app.features.onboarding.home.domain.repository

import com.example.mobile_client_app.features.onboarding.home.data.remote.HomeAPI
import com.example.mobile_client_app.features.onboarding.home.data.remote.model.BannerResponse
import com.example.mobile_client_app.features.onboarding.home.data.remote.model.ClassResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class HomeRepositoryImpl(
    private val api: HomeAPI
) : HomeRepository {
    override suspend fun getNotificationCount(): Result<Int, NetworkError> {
        return api.getNotificationCount()
    }

    override suspend fun getBanners(): Result<List<BannerResponse>, NetworkError> {
        return api.getBanners()
    }

    override suspend fun getUpcomingClasses(): Result<List<ClassResponse>, NetworkError> {
        return api.getUpcomingClasses()
    }
}