package com.example.mobile_client_app.auth.registering.presentaion.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mobile_client_app.auth.login.presentation.ui.LoginEvent
import com.example.mobile_client_app.common.component.millisToDate
import com.example.mobile_client_app.common.component.toDDMMYYY
import com.example.mobile_client_app.common.countryPicker.Country
import com.example.mobile_client_app.util.phoneNumberVerification
import com.example.mobile_client_app.util.validName
import com.example.mobile_client_app.util.validNumber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDateTime

sealed interface RegisteringEvent {
    object Reset : RegisteringEvent
    data class ShowSnackbar(val message: String) : RegisteringEvent
}

class RegisteringViewModel() : ViewModel() {
    var firstName by mutableStateOf("")
        private set
    var middleName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var idNumber by mutableStateOf("")
        private set
    var dateOfBirth by mutableStateOf<LocalDateTime?>(null)
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
    var isExpanded by mutableStateOf(false)
        private set
    var phoneNumber by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set

    private val _events = MutableStateFlow<RegisteringEvent?>(null)
    val events: StateFlow<RegisteringEvent?> = _events

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
        dateOfBirth = millisToDate(millis = newDate)
    }

    fun getSelectDateAsString(): String? {
        return dateOfBirth.toDDMMYYY()
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

    fun updateIsExpanded(isExpanded: Boolean) {
        this.isExpanded = isExpanded
    }

    fun updatePhoneNumber(phoneNumber: String) {
        this.phoneNumber = phoneNumberVerification(phoneNumber)
    }

    fun updateEmail(email: String) {
        this.email = email
    }

    fun savePersonalInformation(): Boolean {
        return if (firstName.isBlank() || firstName.length < 3) {
            _events.value = RegisteringEvent.ShowSnackbar("First name is required")
            false
        }else if (lastName.isBlank() || lastName.length < 3) {
            _events.value = RegisteringEvent.ShowSnackbar("Last name is required")
            false
        }else if (dateOfBirth ==null) {
            _events.value = RegisteringEvent.ShowSnackbar("Date Of Birth is required")
            false
        }else if (phoneNumber.isBlank()) {
            _events.value = RegisteringEvent.ShowSnackbar("Phone number is required")
            false
        }else if (email.isBlank()) {
            _events.value = RegisteringEvent.ShowSnackbar("Email is required")
            false
        }else if (password.isBlank()) {
            _events.value = RegisteringEvent.ShowSnackbar("Password is required")
            false
        }else if (passwordStrength < 0.66f){
            _events.value = RegisteringEvent.ShowSnackbar("Password is weak")
            false
        }else {
            true
        }
    }
}