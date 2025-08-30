package com.example.mobile_client_app.features.personalTraining.trainerSelection.presentation.trainingSelection

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.common.models.DateRangePickerConfig
import com.example.mobile_client_app.features.personalTraining.trainerSelection.data.model.AvailableTimeResponse
import com.example.mobile_client_app.features.personalTraining.trainerSelection.data.model.TrainingSpecializationResponse
import com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.usecase.GetAvailableTimeUseCase
import com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.usecase.GetTrainingSpecializationUseCase
import com.example.mobile_client_app.features.personalTraining.trainerSelection.domain.usecase.RequestAppointmentUseCase
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import saschpe.log4k.Log
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class TrainingSelectionScreenUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val list: List<TrainingSpecializationResponse> = emptyList(),
    val isGettingAvailableTime: Boolean = false,
    val isGettingAvailableError: String? = null,
    val availableTimes: List<AvailableTimeResponse> = emptyList(),
    val isRequestingAppointment: Boolean = false,
    val requestingAppointmentError: String? = null,
    val isRequestingAppointmentComparable: Boolean = false,
)

data class TrainingType(val id: Int, val displayName: String)

class TrainingSelectionViewModel(
    private val getTrainingSpecializationUseCase: GetTrainingSpecializationUseCase,
    private val getAvailableTimeUseCase: GetAvailableTimeUseCase,
    private val requestAppointmentUseCase: RequestAppointmentUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrainingSelectionScreenUiState())
    val uiState: StateFlow<TrainingSelectionScreenUiState> = _uiState.asStateFlow()

    var trainingTypes = listOf(TrainingType(1, "Personal Training"), TrainingType(2, "Group Training"))
        private set

    var selectedTrainingType = mutableStateOf(trainingTypes[1])
        private set

    var selectedSpecializationType = mutableStateOf<TrainingSpecializationResponse?>(null)
        private set

    private val selectedDate = mutableStateOf<LocalDate?>(null)

    @OptIn(ExperimentalTime::class)
    var currentMonth = mutableStateOf(Clock.System.todayIn(TimeZone.currentSystemDefault()))

    var errorMessage = mutableStateOf<String?>(null)

    var isTrainingSpecializationDropdownExpanded = mutableStateOf(false)
        private set

    var trainerId = mutableStateOf("")
        private set

    var selectedTimeId = mutableStateOf<Int?>(null)


    private fun getTrainingSpecialization() {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null, list = emptyList())
        viewModelScope.launch {
            getTrainingSpecializationUseCase.invoke(
                trainerId = trainerId.value, trainingTypeId = selectedTrainingType.value.id
            ).onError {
                _uiState.value = _uiState.value.copy(isLoading = false, error = it.displayMessage)
            }.onSuccess {
                _uiState.value = _uiState.value.copy(isLoading = false, list = it)
            }
        }
    }

    fun onRefresh() {
        getTrainingSpecialization()
    }

    fun hasSelectedType(typeId: Int): Boolean {
        return selectedTrainingType.value.id == typeId
    }

    fun updateSelectType(selectedType: TrainingType) {
        selectedTrainingType.value = selectedType
        selectedSpecializationType.value = null
        selectedDate.value = null
        selectedTimeId.value = null
    }

    fun changeTrainingSpecializationDropdownExpanded(boolean: Boolean? = null) {
        isTrainingSpecializationDropdownExpanded.value = boolean ?: !isTrainingSpecializationDropdownExpanded.value
    }

    fun updateSpecializationType(trainingSpecializationResponse: TrainingSpecializationResponse) {
        this.selectedSpecializationType.value = trainingSpecializationResponse
        getAvailableTimeSlots()
    }


    fun getSelectedDate() = selectedDate.value

    fun onPreviousMonthChange() {
        currentMonth.value = currentMonth.value.minus(1, DateTimeUnit.MONTH)
    }

    fun onNextMonthChange() {
        currentMonth.value = currentMonth.value.plus(1, DateTimeUnit.MONTH)
    }

    @OptIn(ExperimentalTime::class)
    fun dateRangeConfig() = DateRangePickerConfig(
        allowPastDates = false,
        minDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
        maxDate = Clock.System.todayIn(TimeZone.currentSystemDefault()).plus(7, DateTimeUnit.DAY),
        allowSingleDate = true,
        allowStartDateSelection = true,
        allowEndDateSelection = false
    )

    fun isTrainingSpecializationSelected(id: Int): Boolean {
        return selectedTrainingType.value.id == id
    }

    fun updateSelectedDate(date: LocalDate?) {
        this.selectedDate.value = date
        getAvailableTimeSlots()
    }

    fun setTrainerId(selectedTrainerId: String) {
        trainerId.value = selectedTrainerId
        getTrainingSpecialization()
    }

    fun isTimeSlotSelected(time: AvailableTimeResponse): Boolean {
        if (selectedTimeId.value == null || !time.isAvailable) return false
        return selectedTimeId.value == time.id
    }

    fun updateSelectedTime(time: AvailableTimeResponse) {
        Log.error { "Selected time: $selectedTimeId" }
        Log.error { "Selected time: $time." }
        if (!time.isAvailable) return
        selectedTimeId.value = time.id
    }

    fun getAvailableTimeSlots() {
        if (selectedSpecializationType.value == null || selectedDate.value == null) return
        selectedTimeId.value = null
        _uiState.value = _uiState.value.copy(
            isGettingAvailableTime = true, isGettingAvailableError = null, availableTimes = emptyList()
        )
        viewModelScope.launch {
            getAvailableTimeUseCase.invoke(
                trainerId = trainerId.value,
                trainingTypeId = selectedTrainingType.value.id,
                selectedSpecializationTypeId = selectedSpecializationType.value!!.id,
                selectedDate = selectedDate.value!!
            ).onError {
                _uiState.value =
                    _uiState.value.copy(isGettingAvailableTime = false, isGettingAvailableError = it.displayMessage)
            }.onSuccess {
                _uiState.value = _uiState.value.copy(isGettingAvailableTime = false, availableTimes = it)
            }
        }
    }

    fun onGetTimeSlot() {
        getAvailableTimeSlots()
    }

    fun isNextButtonEnable(): Boolean {
        return trainerId.value != null &&
                selectedTrainingType.value != null &&
                selectedSpecializationType.value != null &&
                selectedDate.value != null &&
                selectedTimeId.value != null
    }

    fun onRequestAppointment() {
        _uiState.value = _uiState.value.copy(
            isRequestingAppointment = true,
            isRequestingAppointmentComparable = false,
            requestingAppointmentError = null
        )
        viewModelScope.launch {
            requestAppointmentUseCase.invoke(
                trainerId.value,
                selectedTrainingType.value.id,
                selectedSpecializationType.value!!.id,
                selectedDate.value!!,
                selectedTimeId.value!!
            ).onError {
                _uiState.value = _uiState.value.copy(
                    isRequestingAppointment = false,
                    requestingAppointmentError = it.displayMessage
                )
            }.onSuccess {
                _uiState.value = _uiState.value.copy(
                    isRequestingAppointment = false,
                    isRequestingAppointmentComparable = true,
                )
            }
        }
    }
}