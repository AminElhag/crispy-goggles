package com.example.mobile_client_app.features.onboarding.home.domain.repository

import com.example.mobile_client_app.features.onboarding.home.data.remote.model.BannerResponse
import com.example.mobile_client_app.features.onboarding.home.data.remote.model.ClassResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface HomeRepository {
    suspend fun getNotificationCount() : Result<Int, NetworkError>
    suspend fun getBanners() : Result<List<BannerResponse>, NetworkError>
    suspend fun getUpcomingClasses() : Result<List<ClassResponse>, NetworkError>
}