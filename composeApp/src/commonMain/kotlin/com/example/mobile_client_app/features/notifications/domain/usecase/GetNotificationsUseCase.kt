package com.example.mobile_client_app.features.notifications.domain.usecase

import com.example.mobile_client_app.features.notifications.data.remote.NotificationsAPI
import com.example.mobile_client_app.features.notifications.data.remote.models.GetNotificationResponse
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.Result

class GetNotificationsUseCase(
    private val repository: NotificationsAPI
) {
    suspend operator fun invoke(): Result<List<GetNotificationResponse>, NetworkError> {
       return repository.getNotifications()
    }
}