package com.example.mobile_client_app.auth.registering.presentaion.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.auth.registering.domain.model.UserDTO
import com.example.mobile_client_app.auth.registering.domain.usecase.CreateUserUseCase
import com.example.mobile_client_app.common.HearAboutUs
import com.example.mobile_client_app.common.MedicalCondition
import com.example.mobile_client_app.common.NetworkManager
import com.example.mobile_client_app.common.component.millisToDate
import com.example.mobile_client_app.common.component.toDDMMYYY
import com.example.mobile_client_app.common.countryPicker.Country
import com.example.mobile_client_app.util.network.networkError
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import com.example.mobile_client_app.util.phoneNumberVerification
import com.example.mobile_client_app.util.validName
import com.example.mobile_client_app.util.validNumber
import com.mirego.konnectivity.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.select_any_medical_conditions
import org.jetbrains.compose.resources.getString
import saschpe.log4k.Log

sealed interface RegisteringEvent {
    object Reset : RegisteringEvent
    data class ShowSnackbar(val message: String) : RegisteringEvent
    data class ResponseSuccess(val boolean: Boolean) : RegisteringEvent
}

class RegisteringViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    var firstName by mutableStateOf("")
        private set
    var middleName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var idNumber by mutableStateOf("")
        private set
    var dataOfBirth by mutableStateOf<LocalDateTime?>(null)
        private set
    var showDatePicker by mutableStateOf(false)
        private set
    var isMale by mutableStateOf(true)
        private set
    var password by mutableStateOf("")
        private set
    var isPasswordVisible by mutableStateOf(false)
        private set
    var passwordStrength by mutableStateOf(0f)
        private set
    var selectedCountry by mutableStateOf<Country?>(null)
        private set
    var isCountrySelectorExpanded by mutableStateOf(false)
        private set
    var isHearAboutUsSelectorExpanded by mutableStateOf(false)
        private set
    var phoneNumber by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var emergencyContact by mutableStateOf("")
        private set
    var hearAboutUs by mutableStateOf<HearAboutUs?>(null)
        private set
    var occupation by mutableStateOf("")
        private set
    var medicalConditions = mutableStateListOf<MedicalCondition>()
        private set
    var isMedicalConditionExpanded by mutableStateOf(false)
        private set

    private val _events = MutableStateFlow<RegisteringEvent?>(null)
    val events: StateFlow<RegisteringEvent?> = _events

    var isLoading by mutableStateOf(false)

    var isConnected = false

    private val key = stringPreferencesKey("token")

    //need this code on start of app to know if token is available or not
    /*    private var _name = MutableStateFlow("")
        val name = _name.asStateFlow()

        init {
            viewModelScope.launch {
                dataStore.data.collect { storedData ->
                    _name.update {
                        storedData.get(key).orEmpty()
                    }
                }
            }
        }*/

    fun updateFirstName(newName: String) {
        firstName = validName(newName) ?: return
    }

    fun updateMiddleName(newName: String) {
        middleName = validName(newName) ?: return
    }

    fun updateLastName(newName: String) {
        lastName = validName(newName) ?: return
    }

    fun updateIdNumber(newNumber: String) {
        idNumber = validNumber(newNumber) ?: return
    }

    fun updateShowDatePicker(isShowDatePicker: Boolean) {
        showDatePicker = isShowDatePicker
    }

    fun updateDateOfBirth(newDate: Long) {
        dataOfBirth = millisToDate(millis = newDate)
    }

    fun getSelectDateAsString(): String? {
        return dataOfBirth.toDDMMYYY()
    }

    fun updateIsMale(isMale: Boolean) {
        this.isMale = isMale
    }

    fun updatePassword(newPassword: String) {
        this.password = newPassword
        updatePasswordStrength()
    }

    fun updatePasswordVisibility(isVisible: Boolean) {
        isPasswordVisible = isVisible
    }

    fun updatePasswordStrength() {
        passwordStrength = 0.0f
        if (password.isEmpty()) passwordStrength = 0.0f

        when {
            password.length < 6 -> passwordStrength += 0.1f
            password.length in 6..8 -> passwordStrength += 0.2f
            password.length in 9..12 -> passwordStrength += 0.3f
            password.length in 13..16 -> passwordStrength += 0.4f
            password.length > 16 -> passwordStrength += 0.4f
        }

        // Check for numbers
        if (password.any { it.isDigit() }) passwordStrength += 0.2f

        // Check for symbols
        if (password.any { !it.isLetterOrDigit() }) passwordStrength += 0.2f

        // Check for uppercase letters
        if (password.any { it.isUpperCase() }) passwordStrength += 0.2f

        // Check for lowercase letters
        if (password.any { it.isLowerCase() }) passwordStrength += 0.2f

        // Normalize the strength to max 1.0f
        passwordStrength.coerceAtMost(1.0f)
    }

    fun updateCountry(country: Country) {
        this.selectedCountry = country
    }

    fun updateIsCountrySelectorExpanded(isExpanded: Boolean) {
        this.isCountrySelectorExpanded = isExpanded
    }

    fun updateIsHearAboutUsSelectorExpanded(isExpanded: Boolean) {
        this.isHearAboutUsSelectorExpanded = isExpanded
    }

    fun updateIsMedicalConditionExpanded(isExpanded: Boolean) {
        this.isMedicalConditionExpanded = isExpanded
    }

    fun updatePhoneNumber(phoneNumber: String) {
        this.phoneNumber = phoneNumberVerification(phoneNumber)
    }

    fun updateEmail(email: String) {
        this.email = email
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

    fun savePersonalInformation(): Boolean {
        return if (firstName.isBlank() || firstName.length < 3) {
            _events.value = RegisteringEvent.ShowSnackbar("First name is required")
            false
        } else if (lastName.isBlank() || lastName.length < 3) {
            _events.value = RegisteringEvent.ShowSnackbar("Last name is required")
            false
        } else if (dataOfBirth == null) {
            _events.value = RegisteringEvent.ShowSnackbar("Date Of Birth is required")
            false
        } else if (phoneNumber.isBlank()) {
            _events.value = RegisteringEvent.ShowSnackbar("Phone number is required")
            false
        } else if (email.isBlank()) {
            _events.value = RegisteringEvent.ShowSnackbar("Email is required")
            false
        } else if (password.isBlank()) {
            _events.value = RegisteringEvent.ShowSnackbar("Password is required")
            false
        } else if (passwordStrength < 0.66f) {
            _events.value = RegisteringEvent.ShowSnackbar("Password is weak")
            false
        } else {
            true
        }
    }

    fun resetEvent() {
        _events.value = RegisteringEvent.Reset
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

    fun checkInternetConnection() {
        viewModelScope.launch {
            NetworkManager.networkState.collectLatest { networkState ->
                Log.debug { "Internet is connection : ${networkState}" }
                isConnected = when (networkState) {
                    is NetworkState.Reachable -> true
                    NetworkState.Unreachable -> false
                }
            }
        }
        Log.debug { "Internet is connection : $isConnected" }
    }

    fun createUser() {
        if (emergencyContact.isBlank()) _events.value = RegisteringEvent.ShowSnackbar("Emergency contact is required")
        else if (hearAboutUs == null) _events.value = RegisteringEvent.ShowSnackbar("How did you hear about us?")
        else if (occupation.isBlank()) _events.value = RegisteringEvent.ShowSnackbar("Occupation is required")
        else sendCreateRequest()
    }

    fun sendCreateRequest() {
        checkInternetConnection()
        if (!isConnected) {
            _events.value = RegisteringEvent.ShowSnackbar("Internet is not connected")
        } else {
            isLoading = true
            Log.debug { "Birth Date: ${dataOfBirth?.date}" }
            viewModelScope.launch {
                createUserUseCase.invoke(
                    UserDTO(
                        firstName = firstName.trim(),
                        middleName = lastName.trim(),
                        lastName = lastName.trim(),
                        idNumber = idNumber.trim(),
                        dataOfBirth = dataOfBirth,
                        genderId = if (isMale) 0 else 1,
                        phoneNumber = phoneNumber.trim(),
                        email = email.trim(),
                        password = password.trim(),
                        emergencyContact = emergencyContact.trim(),
                        hearAboutUsId = hearAboutUs?.id!!,
                        occupation = occupation.trim(),
                        medicalConditionsIds = medicalConditions.map { it.id }
                    )
                )?.onError {
                    isLoading = false
                    _events.value = RegisteringEvent.ShowSnackbar(message = networkError(it))
                }?.onSuccess { response ->
                    viewModelScope.launch {
                        dataStore.updateData {
                            it.toMutablePreferences().apply {
                                set(key,response.token!!)
                            }
                        }
                    }
                    _events.value = RegisteringEvent.ResponseSuccess(true)
                    isLoading = false
                }
            }
        }
    }
}