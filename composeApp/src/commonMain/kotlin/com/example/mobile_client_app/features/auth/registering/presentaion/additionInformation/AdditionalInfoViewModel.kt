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
import com.example.mobile_client_app.features.auth.registering.presentaion.registering.PersonalInfoData
import com.example.mobile_client_app.util.network.checkInternetConnection
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.select_any_medical_conditions
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
    var isConnected = false

    // Store personal info data received from previous screen
    private var personalInfoData: PersonalInfoData? = null

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
    }

    fun removeMedicalCondition(medicalCondition: MedicalCondition) {
        medicalConditions.remove(medicalCondition)
    }

    fun getMedicalConditionsAsString(): String {
        var medicalConditionsString = ""
        if (medicalConditions.isEmpty()) {
            viewModelScope.launch {
                medicalConditionsString = getString(Res.string.select_any_medical_conditions)
            }
            return medicalConditionsString
        }
        medicalConditionsString = ""
        medicalConditions.forEach {
            viewModelScope.launch {
                medicalConditionsString += "${getString(it.name)}, "
            }
        }
        return medicalConditionsString
    }

    fun isMedicalConditionIsSelected(id: Int): Boolean {
        medicalConditions.forEach {
            if (it.id == id) return true
        }
        return false
    }

    fun createUser() {
        if (emergencyContact.isBlank()) _events.value = AdditionalInfoEvent.ShowSnackbar("Emergency contact is required")
        else if (hearAboutUs == null) _events.value = AdditionalInfoEvent.ShowSnackbar("How did you hear about us?")
        else if (occupation.isBlank()) _events.value = AdditionalInfoEvent.ShowSnackbar("Occupation is required")
        else sendCreateRequest()
    }

    fun sendCreateRequest() {
        isConnected = checkInternetConnection()
        if (!isConnected) {
            _events.value = AdditionalInfoEvent.ShowSnackbar("Internet is not connected")
        } else {
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
                            response.token.let { token ->
                                set(TOKEN_KEY, token)
                            }
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
    }

    fun resetEvent() {
        _events.value = AdditionalInfoEvent.Reset
    }
}