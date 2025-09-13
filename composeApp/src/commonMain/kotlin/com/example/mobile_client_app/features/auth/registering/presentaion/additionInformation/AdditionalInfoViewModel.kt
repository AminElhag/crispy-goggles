package com.example.mobile_client_app.features.auth.registering.presentaion.additionInformation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.common.HearAboutUs
import com.example.mobile_client_app.common.MedicalCondition
import com.example.mobile_client_app.common.TOKEN_KEY
import com.example.mobile_client_app.features.auth.registering.domain.model.UserDTO
import com.example.mobile_client_app.features.auth.registering.domain.usecase.CreateUserUseCase
import com.example.mobile_client_app.features.auth.registering.presentaion.AdditionalInfoState
import com.example.mobile_client_app.features.auth.registering.presentaion.registering.PersonalInfoData
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import saschpe.log4k.Log

sealed interface AdditionalInfoEvent {
    object Reset : AdditionalInfoEvent
    data class ShowSnackbar(val message: String) : AdditionalInfoEvent
    data class ResponseSuccess(val boolean: Boolean) : AdditionalInfoEvent
}

class AdditionalInfoViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    var emergencyContact by mutableStateOf("")
        private set
    var hearAboutUs by mutableStateOf<HearAboutUs?>(null)
        private set
    var occupation by mutableStateOf("")
        private set
    var medicalConditions = mutableStateListOf<MedicalCondition>()
        private set
    var isHearAboutUsSelectorExpanded by mutableStateOf(false)
        private set
    var isMedicalConditionExpanded by mutableStateOf(false)
        private set

    private val _events = MutableStateFlow<AdditionalInfoEvent?>(null)
    val events: StateFlow<AdditionalInfoEvent?> = _events

    var isLoading by mutableStateOf(false)

    private var personalInfoData: PersonalInfoData? = null

    var medicalConditionsDisplayText by mutableStateOf("")
        private set

    fun restoreState(state: AdditionalInfoState) {
        emergencyContact = state.emergencyContact
        occupation = state.occupation
        hearAboutUs = state.hearAboutUs
        medicalConditions.clear()
        medicalConditions.addAll(state.medicalConditions)
        isHearAboutUsSelectorExpanded = state.isHearAboutUsSelectorExpanded
        isMedicalConditionExpanded = state.isMedicalConditionExpanded
    }

    fun saveCurrentState(): AdditionalInfoState {
        return AdditionalInfoState(
            emergencyContact = emergencyContact,
            occupation = occupation,
            hearAboutUs = hearAboutUs,
            medicalConditions = medicalConditions.toList(),
            isHearAboutUsSelectorExpanded = isHearAboutUsSelectorExpanded,
            isMedicalConditionExpanded = isMedicalConditionExpanded
        )
    }

    fun setPersonalInfoData(data: PersonalInfoData) {
        personalInfoData = data
    }

    fun updateOccupation(occupation: String) {
        this.occupation = occupation
    }

    fun updateEmergencyContact(emergencyContact: String) {
        this.emergencyContact = emergencyContact
    }

    fun updateHearAboutUs(hearAboutUs: HearAboutUs) {
        this.hearAboutUs = hearAboutUs
    }

    fun updateIsHearAboutUsSelectorExpanded(isExpanded: Boolean) {
        this.isHearAboutUsSelectorExpanded = isExpanded
    }

    fun updateIsMedicalConditionExpanded(isExpanded: Boolean) {
        this.isMedicalConditionExpanded = isExpanded
    }

    fun addMedicalCondition(medicalCondition: MedicalCondition) {
        medicalConditions.add(medicalCondition)
        updateMedicalConditionsDisplayText()
    }

    fun removeMedicalCondition(medicalCondition: MedicalCondition) {
        medicalConditions.remove(medicalCondition)
        updateMedicalConditionsDisplayText()
    }

    private fun updateMedicalConditionsDisplayText() {
        viewModelScope.launch {
            if (medicalConditions.isEmpty()) {
                medicalConditionsDisplayText = ""
            } else {
                // Use async to handle multiple getString calls concurrently
                val deferredStrings = medicalConditions.map { condition ->
                    async { getString(condition.name) }
                }

                // Await all results and join them
                val conditionNames = deferredStrings.awaitAll()
                medicalConditionsDisplayText = conditionNames.joinToString(", ")
            }
        }
    }
    fun isMedicalConditionIsSelected(id: Int): Boolean {
        medicalConditions.forEach {
            if (it.id == id) return true
        }
        return false
    }

    fun createUser() {
        if (emergencyContact.isBlank()) _events.value =
            AdditionalInfoEvent.ShowSnackbar("Emergency contact is required")
        else if (hearAboutUs == null) _events.value = AdditionalInfoEvent.ShowSnackbar("How did you hear about us?")
        else if (occupation.isBlank()) _events.value = AdditionalInfoEvent.ShowSnackbar("Occupation is required")
        else sendCreateRequest()
    }

    fun sendCreateRequest() {
        val personalInfo = personalInfoData
        if (personalInfo == null) {
            _events.value = AdditionalInfoEvent.ShowSnackbar("Personal information is missing")
            return
        }

        isLoading = true
        viewModelScope.launch {
            createUserUseCase.invoke(
                UserDTO(
                    firstName = personalInfo.firstName.trim(),
                    middleName = personalInfo.middleName.trim(),
                    lastName = personalInfo.lastName.trim(),
                    idNumber = personalInfo.idNumber.trim(),
                    dataOfBirth = personalInfo.dataOfBirth,
                    genderId = if (personalInfo.isMale) 1 else 2,
                    phoneNumber = personalInfo.phoneNumber.trim(),
                    email = personalInfo.email.trim(),
                    password = personalInfo.password.trim(),
                    emergencyContact = emergencyContact.trim(),
                    hearAboutUsId = hearAboutUs?.id!!,
                    occupation = occupation.trim(),
                    medicalConditionsIds = medicalConditions.map { it.id }
                )
            ).onSuccess { response ->
                dataStore.updateData {
                    it.toMutablePreferences().apply {
                        set(TOKEN_KEY, response.token)
                    }
                }
                _events.value = AdditionalInfoEvent.ResponseSuccess(true)
                isLoading = false
            }
                .onError { error ->
                    Log.debug { "Network error : $error" }
                    isLoading = false
                    _events.value = AdditionalInfoEvent.ShowSnackbar(message = error.displayMessage)
                }
        }
    }

    fun resetEvent() {
        _events.value = AdditionalInfoEvent.Reset
    }
}