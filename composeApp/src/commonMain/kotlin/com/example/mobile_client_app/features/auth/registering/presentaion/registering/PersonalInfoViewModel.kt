package com.example.mobile_client_app.features.auth.registering.presentaion.registering

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mobile_client_app.common.component.millisToDate
import com.example.mobile_client_app.common.countryPicker.Country
import com.example.mobile_client_app.util.phoneNumberVerification
import com.example.mobile_client_app.util.validName
import com.example.mobile_client_app.util.validNumber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

sealed interface PersonalInfoEvent {
    object Reset : PersonalInfoEvent
    data class ShowSnackbar(val message: String) : PersonalInfoEvent
    data class ValidationSuccess(val personalInfo: PersonalInfoData) : PersonalInfoEvent
}

data class PersonalInfoData(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val idNumber: String,
    val dataOfBirth: LocalDate?,
    val isMale: Boolean,
    val selectedCountry: Country?,
    val phoneNumber: String,
    val email: String,
    val password: String
)

class PersonalInfoViewModel : ViewModel() {
    var firstName by mutableStateOf("")
        private set
    var middleName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var idNumber by mutableStateOf("")
        private set
    var dataOfBirth by mutableStateOf<LocalDate?>(null)
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
    var phoneNumber by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set

    private val _events = MutableStateFlow<PersonalInfoEvent?>(null)
    val events: StateFlow<PersonalInfoEvent?> = _events

    @OptIn(ExperimentalTime::class)
    var today = Clock.System.now().toEpochMilliseconds()
        private set

    @OptIn(ExperimentalTime::class)
    var maxDate = Clock.System.now().plus(duration = 7.days).toEpochMilliseconds()
        private set

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
        return dataOfBirth?.toString()
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

        if (password.any { it.isDigit() }) passwordStrength += 0.2f

        if (password.any { !it.isLetterOrDigit() }) passwordStrength += 0.2f

        if (password.any { it.isUpperCase() }) passwordStrength += 0.2f

        if (password.any { it.isLowerCase() }) passwordStrength += 0.2f

        passwordStrength.coerceAtMost(1.0f)
    }

    fun updateCountry(country: Country) {
        this.selectedCountry = country
    }

    fun updateIsCountrySelectorExpanded(isExpanded: Boolean) {
        this.isCountrySelectorExpanded = isExpanded
    }

    fun updatePhoneNumber(phoneNumber: String) {
        this.phoneNumber = phoneNumberVerification(phoneNumber)
    }

    fun updateEmail(email: String) {
        this.email = email
    }

    fun savePersonalInformation(): Boolean {
        return if (firstName.isBlank() || firstName.length < 3) {
            _events.value = PersonalInfoEvent.ShowSnackbar("First name is required")
            false
        } else if (lastName.isBlank() || lastName.length < 3) {
            _events.value = PersonalInfoEvent.ShowSnackbar("Last name is required")
            false
        } else if (dataOfBirth == null) {
            _events.value = PersonalInfoEvent.ShowSnackbar("Date Of Birth is required")
            false
        } else if (phoneNumber.isBlank()) {
            _events.value = PersonalInfoEvent.ShowSnackbar("Phone number is required")
            false
        } else if (email.isBlank()) {
            _events.value = PersonalInfoEvent.ShowSnackbar("Email is required")
            false
        } else if (password.isBlank()) {
            _events.value = PersonalInfoEvent.ShowSnackbar("Password is required")
            false
        } else if (passwordStrength < 0.66f) {
            _events.value = PersonalInfoEvent.ShowSnackbar("Password is weak")
            false
        } else {
            val personalInfo = PersonalInfoData(
                firstName = firstName,
                middleName = middleName,
                lastName = lastName,
                idNumber = idNumber,
                dataOfBirth = dataOfBirth,
                isMale = isMale,
                selectedCountry = selectedCountry,
                phoneNumber = phoneNumber,
                email = email,
                password = password
            )
            _events.value = PersonalInfoEvent.ValidationSuccess(personalInfo)
            true
        }
    }

    fun resetEvent() {
        _events.value = PersonalInfoEvent.Reset
    }
}