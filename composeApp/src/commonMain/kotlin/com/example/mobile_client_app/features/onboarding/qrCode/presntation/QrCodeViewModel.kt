package com.example.mobile_client_app.features.onboarding.qrCode.presntation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.features.onboarding.qrCode.domain.usecase.GetQrCodeDataUseCase
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class QrCodeScreenUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: String? = null,
)

class QrCodeViewModel(
    private val getQrCodeDataUseCase: GetQrCodeDataUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(QrCodeScreenUiState())
    val uiState: StateFlow<QrCodeScreenUiState> = _uiState.asStateFlow()

    init {
        sendRequest()
    }


    fun refresh() {
        sendRequest()
    }

    fun sendRequest() {
        _uiState.value = _uiState.value.copy(isLoading = true, data = null, error = null)
        viewModelScope.launch {
            getQrCodeDataUseCase.invoke().onError { error ->
                _uiState.value = _uiState.value.copy(isLoading = false, error = error.displayMessage)
            }.onSuccess { data ->
                _uiState.value = _uiState.value.copy(isLoading = false, data = data.toString())
            }
        }
    }
}