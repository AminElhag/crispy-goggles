package com.example.mobile_client_app.features.auth.profile.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.features.auth.profile.data.remote.models.ProfileResponse
import com.example.mobile_client_app.features.auth.profile.domain.models.DateRange
import com.example.mobile_client_app.features.auth.profile.domain.models.getMemberStatus
import com.example.mobile_client_app.features.auth.profile.domain.useCase.GetProfileUseCase
import com.example.mobile_client_app.features.auth.profile.domain.useCase.SendFreezingRequestUseCase
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.unknown_membership
import org.jetbrains.compose.resources.StringResource

data class ProfileScreenUiState(
    val isLoading: Boolean = false,
    val data: ProfileResponse? = null,
    val error: String? = null,
)

sealed class ProfileScreenUiEvent {
    object Refresh : ProfileScreenUiEvent()
    data class ShowSnackbar(val message: String) : ProfileScreenUiEvent()
    object Reset : ProfileScreenUiEvent()
    object Logout : ProfileScreenUiEvent()
}

class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val sendFreezingRequestUseCase: SendFreezingRequestUseCase,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileScreenUiState())
    val uiState: StateFlow<ProfileScreenUiState> = _uiState.asStateFlow()

    private val _event = MutableStateFlow<ProfileScreenUiEvent>(ProfileScreenUiEvent.Reset)
    val event: StateFlow<ProfileScreenUiEvent> = _event

    private val selectedDateRange = mutableStateOf(DateRange(null, null))
    private val showFreezingDialog = mutableStateOf(false)
    private val showLoadingDialog = mutableStateOf(false)

    init {
        getProfileData()
    }

    fun refresh() {
        getProfileData()
    }

    private fun getProfileData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, data = null)
            getProfileUseCase.invoke().onError { error ->
                _uiState.value = _uiState.value.copy(isLoading = false, error = error.displayMessage)
            }.onSuccess { data ->
                _uiState.value = _uiState.value.copy(isLoading = false, error = null, data = data)
            }
        }
    }

    fun getChangeColor(isPositiveChange: Boolean?): Color? {
        return when (isPositiveChange) {
            true -> Color(0xFF4CAF50)  // Green
            false -> Color(0xFFF44336) // Red
            null -> null
        }
    }

    fun memberStatus(membershipStatus: Int): StringResource {
        return getMemberStatus(membershipStatus) ?: Res.string.unknown_membership
    }

    fun setDateRange(dateRange: DateRange) {
        selectedDateRange.value = dateRange
    }

    fun getStartDate() = selectedDateRange.value.startDate

    fun getEndDate() = selectedDateRange.value.endDate

    fun getShowFreezingDialog() = showFreezingDialog.value

    fun getShowLoadingDialog() = showLoadingDialog.value

    fun setShowFreezingDialog(value: Boolean) {
        showFreezingDialog.value = value
    }

    fun resetEvent() {
        _event.value = ProfileScreenUiEvent.Reset
    }

    fun deleteLoginInformation() {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences.clear()
                _event.value = ProfileScreenUiEvent.Logout
            }
        }
    }

    fun onFreezingSend() {
        println(selectedDateRange.value)
        showFreezingDialog.value = false
        showLoadingDialog.value = true
        viewModelScope.launch {
            sendFreezingRequestUseCase.invoke(selectedDateRange.value).onError {
                showLoadingDialog.value = false
                _event.value = ProfileScreenUiEvent.ShowSnackbar(it.displayMessage)
            }.onSuccess {
                showLoadingDialog.value = false
                refresh()
            }
        }
    }
}