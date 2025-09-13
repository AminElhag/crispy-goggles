package com.example.mobile_client_app.features.notifications.data.remote

import com.example.mobile_client_app.features.notifications.data.remote.models.GetNotificationDetailsResponse
import com.example.mobile_client_app.features.notifications.data.remote.models.GetNotificationResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result
import com.example.mobile_client_app.util.network.convertToNetworkError
import com.example.mobile_client_app.util.network.toException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class NotificationsAPIImpl(
    private val httpClient: HttpClient
) : NotificationsAPI {
    override suspend fun getNotifications(): Result<List<GetNotificationResponse>, NetworkError> {
        val response = try {
            httpClient.get("/api/v1/notifications")
        } catch (e: NetworkError) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(convertToNetworkError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<List<GetNotificationResponse>>()
                Result.Success(response)
            }

            else -> Result.Error(response.toException())
        }
    }

    override suspend fun getNotification(notificationId: Long): Result<GetNotificationDetailsResponse, NetworkError> {
        val response = try {
            httpClient.get("/api/v1/notifications"){
                parameter("notificationId", notificationId)
            }
        } catch (e: NetworkError) {
            return Result.Error(e)
        } catch (e: Exception) {
            return Result.Error(convertToNetworkError(e))
        }
        return when (response.status.value) {
            in 200..299 -> {
                val response = response.body<GetNotificationDetailsResponse>()
                Result.Success(response)
            }

            else -> Result.Error(response.toException())
        }
    }
}