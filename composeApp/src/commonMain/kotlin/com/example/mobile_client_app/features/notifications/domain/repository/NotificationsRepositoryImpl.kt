package com.example.mobile_client_app.features.notifications.domain.repository

import com.example.mobile_client_app.features.notifications.data.remote.NotificationsAPI
import com.example.mobile_client_app.features.notifications.data.remote.models.GetNotificationDetailsResponse
import com.example.mobile_client_app.features.notifications.data.remote.models.GetNotificationResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class NotificationsRepositoryImpl(
    private val api: NotificationsAPI,
) : NotificationsRepository {
    override suspend fun getNotifications(): Result<List<GetNotificationResponse>, NetworkError> {
        return api.getNotifications()
    }

    override suspend fun getNotification(notificationId: Long): Result<GetNotificationDetailsResponse, NetworkError> {
        return api.getNotification(notificationId)
    }
}