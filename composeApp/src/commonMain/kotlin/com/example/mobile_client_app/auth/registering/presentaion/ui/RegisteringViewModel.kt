package com.example.mobile_client_app.auth.registering.presentaion.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mobile_client_app.common.component.millisToDate
import com.example.mobile_client_app.common.component.toDDMMYYY
import com.example.mobile_client_app.common.countryPicker.Country
import kotlinx.datetime.LocalDateTime

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


    fun updateFirstName(newName: String) {
        firstName = newName
    }

    fun updateMiddleName(newName: String) {
        middleName = newName
    }

    fun updateLastName(newName: String) {
        lastName = newName
    }

    fun updateIdNumber(newNumber: String) {
        idNumber = newNumber
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
        this.phoneNumber = phoneNumber
    }

    fun updateEmail(email: String) {
        this.email = email
    }
}