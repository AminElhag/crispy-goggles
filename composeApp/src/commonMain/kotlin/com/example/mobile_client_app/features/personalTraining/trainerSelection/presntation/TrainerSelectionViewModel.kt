package com.example.mobile_client_app.features.personalTraining.trainerSelection.presntation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.features.personalTraining.appointments.data.models.TrainerResponse
import com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.usecase.GetTrainersUseCase
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class TrainerSelectionScreenUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val trainerResponses: List<TrainerResponse> = emptyList(),
)

class TrainerSelectionViewModel(
    private val getTrainersUseCase: GetTrainersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TrainerSelectionScreenUiState())
    val uiState: StateFlow<TrainerSelectionScreenUiState> = _uiState.asStateFlow()

    init {
        getTrainers()
    }

    fun getTrainers() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            getTrainersUseCase.invoke().onError {
                _uiState.value = _uiState.value.copy(isLoading = false, error = it.displayMessage)
            }.onSuccess {
                _uiState.value = _uiState.value.copy(isLoading = false, trainerResponses = it)
            }
        }
    }

    fun onRefresh() {
        getTrainers()
    }

}