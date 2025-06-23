package com.example.mobile_client_app.auth.login.presentation.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.auth.login.domain.usecase.LoginUseCase
import com.example.mobile_client_app.common.CountryPicker.Country
import com.example.mobile_client_app.common.NetworkManager
import com.example.mobile_client_app.util.NetworkError
import com.example.mobile_client_app.util.onError
import com.example.mobile_client_app.util.onSuccess
import com.mirego.konnectivity.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

sealed interface LoginEvent {
    data class ShowSnackbar(val message: String) : LoginEvent
}

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    var emailOrPhone by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<NetworkError?>(null)
    var successMessage by mutableStateOf("")

    val numericRegex = Regex("[^0-9]")


    var phone by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var isPhoneSelected by mutableStateOf(true)
        private set
    var country by mutableStateOf<Country?>(null)
        private set

    var snackbarMessage by mutableStateOf("")
    var showSnackbar by mutableStateOf(false)
        private set

    private val _events = MutableStateFlow<LoginEvent?>(null)
    val events: StateFlow<LoginEvent?> = _events

    private var isConnected = false

    fun login() {
        print("LOGIN FUN")
        if (!checkInternetConnection()){
            print("outInside the viewmodel scope")
            viewModelScope.launch {
                print("Inside the viewmodel scope")
                _events.value = LoginEvent.ShowSnackbar("Internet is not connected")
            }
            return
        }
        if (emailOrPhone.isEmpty() || password.isEmpty()) {
            viewModelScope.launch {
                _events.value = LoginEvent.ShowSnackbar("Please enter a valid email address")
            }
            return
        }
        setEmailOrPhone()
        if (emailOrPhone.isEmpty() || password.isEmpty()) {
            errorMessage = NetworkError.NO_INTERNET
            showSnackbar("Login failed")
            return
        }
        viewModelScope.launch {
            isLoading = true
            loginUseCase(emailOrPhone, password)
                .onError {
                    isLoading = false
                    errorMessage = it
                    showSnackbar("Please enter email or phone and password")
                }.onSuccess {
                    isLoading = false
                    successMessage = it.token
                }
        }
    }

    private fun setEmailOrPhone() {
        emailOrPhone = if (isPhoneSelected) {
            country!!.code + phone
        } else {
            email
        }
    }

    fun updateEmail(input: String) {
        email = input
    }

    fun updatePhone(input: String) {
        val stripped = numericRegex.replace(input, "")
        phone = if (stripped.length >= 10) {
            stripped.substring(0..9)
        } else {
            stripped
        }
    }

    val phoneHasErrors by derivedStateOf {
        phone.isNotEmpty()
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
        return phone.isNotEmpty() || email.isNotEmpty()
    }

    private fun showSnackbar(message: String) {
        snackbarMessage = message
        showSnackbar = true
    }

    fun checkInternetConnection(): Boolean {
        print("Check internet connection")
        viewModelScope.launch {
            NetworkManager.networkState.collectLatest { networkState ->
                isConnected = when (networkState) {
                    is NetworkState.Reachable -> true
                    NetworkState.Unreachable -> false
                }
            }
        }
        print("Internet connection : $isConnected")
        return isConnected
    }
}