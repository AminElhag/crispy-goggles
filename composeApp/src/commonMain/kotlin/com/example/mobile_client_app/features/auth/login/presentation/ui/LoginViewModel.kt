package com.example.mobile_client_app.features.auth.login.presentation.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.common.TOKEN_KEY
import com.example.mobile_client_app.common.countryPicker.Country
import com.example.mobile_client_app.features.auth.login.domain.usecase.LoginUseCase
import com.example.mobile_client_app.features.auth.registering.presentaion.additionInformation.AdditionalInfoEvent
import com.example.mobile_client_app.util.network.checkInternetConnection
import com.example.mobile_client_app.util.network.onError
import com.example.mobile_client_app.util.network.onSuccess
import com.example.mobile_client_app.util.phoneNumberVerification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface LoginEvent {
    object Reset : LoginEvent
    data class ShowSnackbar(val message: String) : LoginEvent
    object Login : LoginEvent
}

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    var emailOrPhone by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var phoneNumber by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var isPhoneSelected by mutableStateOf(true)
        private set
    var country by mutableStateOf<Country?>(null)
        private set

    private val _events = MutableStateFlow<LoginEvent?>(null)
    val events: StateFlow<LoginEvent?> = _events

    var isConnected = false

    fun login() {
        isConnected = checkInternetConnection()
        viewModelScope.launch {
            if (!isConnected) {
                _events.value = LoginEvent.ShowSnackbar("Internet is not connected")
            } else if (isPhoneSelected && phoneNumber.isBlank()) {
                _events.value = LoginEvent.ShowSnackbar("Phone number should be specified")
            } else if (!isPhoneSelected && email.isBlank()) {
                _events.value = LoginEvent.ShowSnackbar("Email should be specified")
            } else if (password.isBlank()) {
                _events.value = LoginEvent.ShowSnackbar("Password should be specified")
            } else {
                setEmailOrPhone()
                isLoading = true
                loginUseCase.invoke(emailOrPhone, password)
                    .onSuccess { response ->
                        isLoading = false
                        _events.value = LoginEvent.Reset
                        dataStore.updateData {
                            it.toMutablePreferences().apply {
                                set(TOKEN_KEY, response.accessToken!!)
                            }
                        }
                        _events.value = LoginEvent.Login
                    }
                    .onError {
                        isLoading = false
                        _events.value = LoginEvent.ShowSnackbar(message = it.message ?: "Unknown error")
                    }
            }
        }
    }

    private fun setEmailOrPhone() {
        emailOrPhone = if (isPhoneSelected) {
            country!!.code + phoneNumber
        } else {
            email
        }
    }

    fun updateEmail(input: String) {
        email = input
    }

    fun updatePhone(input: String) {
        phoneNumber = phoneNumberVerification(input)
    }

    val phoneHasErrors by derivedStateOf {
        phoneNumber.isNotEmpty()
    }

    fun updatePassword(input: String) {
        password = input
    }

    fun isPhoneSelected(bool: Boolean) {
        isPhoneSelected = bool
    }

    fun updateCountry(country: Country) {
        this.country = country
    }

    fun isPasswordEnabled(): Boolean {
        return phoneNumber.isNotEmpty() || email.isNotEmpty()
    }

    fun resetEvent() {
        _events.value = LoginEvent.Reset
    }
}