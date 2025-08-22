package com.example.mobile_client_app.features.personalTraining.appointments.presntation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.features.personalTraining.appointments.domain.model.Appointment
import com.example.mobile_client_app.features.personalTraining.appointments.domain.usecase.CancelAppointmentUseCase
import com.example.mobile_client_app.features.personalTraining.appointments.domain.usecase.GetAppointmentUseCase
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AppointmentsScreenUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedFilter: Appointment.AppointmentStatus = Appointment.AppointmentStatus.SCHEDULED,
    val filteredAppointments: List<Appointment> = emptyList(),
)

class AppointmentsViewModel(
    private val getAppointmentUseCase: GetAppointmentUseCase,
    private val cancelAppointmentUseCase: CancelAppointmentUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppointmentsScreenUiState())
    val uiState: StateFlow<AppointmentsScreenUiState> = _uiState.asStateFlow()

    private val _appointments = MutableStateFlow<List<Appointment>>(value = emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments.asStateFlow()

    var hasLoading = mutableStateOf(false)
        private set

    var errorDialogVisible = mutableStateOf(false)
        private set

    var cancelWarringDialogVisible = mutableStateOf(false)
        private set

    var errorDialogMessage = mutableStateOf<String?>(null)
        private set

    var cancelAppointmentID: String? = null

    init {
        getAppointments()
    }

    fun getAppointments() {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null, filteredAppointments = emptyList())
        viewModelScope.launch {
            getAppointmentUseCase.invoke().onError {
                _uiState.value = _uiState.value.copy(isLoading = false, error = it.message)
            }.onSuccess {
                _uiState.value = _uiState.value.copy(isLoading = false)
                _appointments.value = it.map { it.toDto() }
                updateFilteredAppointments()
            }
        }
    }

    fun onFilterChanged(status: Appointment.AppointmentStatus) {
        _uiState.value = _uiState.value.copy(selectedFilter = status)
        updateFilteredAppointments()
    }

    /*fun onBookSessionClick() {
        _uiState.value = _uiState.value.copy(showBookingDialog = true)
    }*/

    /*fun onBookingDialogDismiss() {
        _uiState.value = _uiState.value.copy(showBookingDialog = false)
    }*/

    fun cancelAppointment(appointmentId: String?) {
        if (appointmentId == null) return
        changeCancelWarringDialogVisible()
        hasLoading.value = true
        viewModelScope.launch {
            cancelAppointmentUseCase.invoke(appointmentId).onError {
                hasLoading.value = false
                errorDialogMessage.value = it.displayMessage
                errorDialogVisible.value = true
            }.onSuccess {
                hasLoading.value = false
                onRefresh()
            }
        }
    }

    private fun updateFilteredAppointments() {
        val filtered = when (_uiState.value.selectedFilter) {
            Appointment.AppointmentStatus.SCHEDULED -> _appointments.value.filter {
                it.status == Appointment.AppointmentStatus.SCHEDULED
            }

            Appointment.AppointmentStatus.IN_PROGRESS -> _appointments.value.filter {
                it.status == Appointment.AppointmentStatus.IN_PROGRESS
            }

            Appointment.AppointmentStatus.COMPLETED -> _appointments.value.filter {
                it.status == Appointment.AppointmentStatus.COMPLETED
            }

            Appointment.AppointmentStatus.CANCELLED -> _appointments.value.filter {
                it.status == Appointment.AppointmentStatus.CANCELLED
            }
        }
        _uiState.value = _uiState.value.copy(filteredAppointments = filtered)
    }

    fun onRefresh() {
        getAppointments()
    }

    fun changeErrorDialogVisible() {
        errorDialogVisible.value = false
    }

    fun changeCancelWarringDialogVisible() {
        cancelWarringDialogVisible.value = false
    }

    fun showCancelWarringDialog(appointmentId: String) {
        cancelWarringDialogVisible.value = true
        cancelAppointmentID = appointmentId
    }

    fun clearCancelAppointment() {
        changeCancelWarringDialogVisible()
        cancelAppointmentID = null
    }
}