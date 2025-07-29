package com.example.mobile_client_app.features.notifications.data.remote

import com.example.mobile_client_app.features.notifications.data.remote.models.GetNotificationDetailsResponse
import com.example.mobile_client_app.features.notifications.data.remote.models.GetNotificationResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

interface NotificationsAPI {
    suspend fun getNotifications() : Result<List<GetNotificationResponse>, NetworkError>
    suspend fun getNotification(notificationId : Long) : Result<GetNotificationDetailsResponse, NetworkError>
}