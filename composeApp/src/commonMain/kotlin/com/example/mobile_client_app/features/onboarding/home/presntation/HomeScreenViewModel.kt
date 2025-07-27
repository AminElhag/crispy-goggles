package com.example.mobile_client_app.features.onboarding.home.presntation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.features.onboarding.home.data.remote.model.BannerResponse
import com.example.mobile_client_app.features.onboarding.home.data.remote.model.ClassResponse
import com.example.mobile_client_app.features.onboarding.home.domain.usercase.GetBannersUseCase
import com.example.mobile_client_app.features.onboarding.home.domain.usercase.GetNotificationCountUseCase
import com.example.mobile_client_app.features.onboarding.home.domain.usercase.GetUpcomingClassesUseCase
import com.example.mobile_client_app.util.network.NetworkError
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeScreenUiState(
    val isLoading: Boolean = false,
    val banners: List<BannerResponse> = emptyList(),
    val notifications: Int = 0,
    val upcomingClasses: List<ClassResponse> = emptyList(),
    val error: String? = null,
    val hasUnreadNotifications: Boolean = false
)

sealed class HomeScreenUiEvent {
    object Refresh : HomeScreenUiEvent()
    data class OnClassClick(val classId: String) : HomeScreenUiEvent()
    data class OnBannerClick(val bannerId: String) : HomeScreenUiEvent()
    object OnNotificationClick : HomeScreenUiEvent()
}

class HomeScreenViewModel(
    private val getNotificationCountUseCase: GetNotificationCountUseCase,
    private val getBannersUseCase: GetBannersUseCase,
    private val getUpcomingClassesUseCase: GetUpcomingClassesUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    var networkError = mutableStateOf<NetworkError?>(null)
        private set

    var notificationCount = mutableStateOf(0)
        private set

    var banners = mutableStateOf<List<BannerResponse>>(emptyList())
        private set

    var upcomingClasses = mutableStateOf<List<ClassResponse>>(emptyList())
        private set

    init {
        println("Application started")
        loadHomeScreenData()
    }

    fun onEvent(event: HomeScreenUiEvent) {
        when (event) {
            is HomeScreenUiEvent.Refresh -> loadHomeScreenData()
            is HomeScreenUiEvent.OnBannerClick -> TODO()
            is HomeScreenUiEvent.OnClassClick -> TODO()
            is HomeScreenUiEvent.OnNotificationClick -> TODO()
        }
    }

    private fun loadHomeScreenData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val notificationsResponse = async { getNotificationCountUseCase.invoke() }
            val bannersResponse = async { getBannersUseCase.invoke() }
            val upcomingResponse = async { getUpcomingClassesUseCase.invoke() }
            notificationsResponse.await().onSuccess { responses ->
                notificationCount.value = responses
            }.onError { error ->
                networkError.value = error
            }
            bannersResponse.await().onSuccess { responses ->
                banners.value = responses
            }.onError { error ->
                networkError.value = error
            }
            upcomingResponse.await().onSuccess { responses ->
                upcomingClasses.value = responses
            }.onError { error ->
                networkError.value = error
            }
            if (notificationsResponse.isCompleted && bannersResponse.isCompleted && upcomingResponse.isCompleted) {
                _uiState.value = _uiState.value.copy(isLoading = false)
                if (networkError.value != null) {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = networkError.value!!.displayMessage)
                }
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = null,
                    banners = banners.value,
                    notifications = notificationCount.value,
                    upcomingClasses = upcomingClasses.value
                )
            }
        }
    }

    fun retry() {
        loadHomeScreenData()
    }

    fun notificationIcon(): ImageVector {
        return  if (notificationCount.value < 0 )
            Icons.Default.NotificationsNone else Icons.Default.NotificationsActive
    }
}