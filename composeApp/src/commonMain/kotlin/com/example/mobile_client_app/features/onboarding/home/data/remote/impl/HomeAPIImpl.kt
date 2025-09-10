package com.example.mobile_client_app.features.onboarding.home.data.remote.impl

import com.example.mobile_client_app.features.onboarding.home.data.remote.HomeAPI
import com.example.mobile_client_app.features.onboarding.home.data.remote.model.BannerResponse
import com.example.mobile_client_app.features.onboarding.home.data.remote.model.ClassResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import com.example.mobile_client_app.util.network.toException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class HomeAPIImpl(
    private val httpClient: HttpClient
) : HomeAPI {
    override suspend fun getNotificationCount(): Result<Int, NetworkError> {
        val response = try {
            httpClient.get("/api/notifications/count")
        } catch (e: NetworkError) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UnknownError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<Int>()
                Result.Success(response)
            }

            else -> Result.Error(response.toException())
        }
    }

    override suspend fun getBanners(): Result<List<BannerResponse>, NetworkError> {
        val response = try {
            httpClient.get("/api/banners")
        } catch (e: NetworkError) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UnknownError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<List<BannerResponse>>()
                Result.Success(response)
            }

            else -> Result.Error(response.toException())
        }
    }

    override suspend fun getUpcomingClasses(): Result<List<ClassResponse>, NetworkError> {
        val response = try {
            httpClient.get("/api/classes/upcoming")
        } catch (e: NetworkError) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UnknownError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<List<ClassResponse>>()
                Result.Success(response)
            }

            else -> Result.Error(response.toException())
        }
    }
}