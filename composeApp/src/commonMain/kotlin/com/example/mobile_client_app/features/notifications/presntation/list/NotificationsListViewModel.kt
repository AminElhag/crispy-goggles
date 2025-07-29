package com.example.mobile_client_app.features.notifications.presntation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.features.notifications.data.remote.models.GetNotificationResponse
import com.example.mobile_client_app.features.notifications.domain.usecase.GetNotificationsUseCase
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class NotificationsListScreenUiState(
    val isLoading: Boolean = false,
    val list: List<GetNotificationResponse> = emptyList(),
    val error: String? = null,
)

class NotificationsListViewModel(
    private val getNotificationsUseCase: GetNotificationsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(NotificationsListScreenUiState())
    val uiState: StateFlow<NotificationsListScreenUiState> = _uiState.asStateFlow()

    init {
        getNotifications()
    }

    private fun getNotifications() {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null, list = emptyList())
        viewModelScope.launch {
            getNotificationsUseCase.invoke().onError { error ->
                _uiState.value = _uiState.value.copy(isLoading = false, error = error.displayMessage, list = emptyList())
            }.onSuccess { data ->
                _uiState.value = _uiState.value.copy(isLoading = false, error = null, list = data)
            }
        }
    }

    fun refresh() {
        getNotifications()
    }
}