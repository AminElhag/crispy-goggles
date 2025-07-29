package com.example.mobile_client_app.features.notifications.domain.usecase

import com.example.mobile_client_app.features.notifications.data.remote.NotificationsAPI

class GetNotificationUseCase(
    private val repository: NotificationsAPI
) {
    suspend operator fun invoke(notificationId: Long) = repository.getNotification(notificationId)
}