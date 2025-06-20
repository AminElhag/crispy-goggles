package com.example.mobile_client_app.auth.login.presentation.ui

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.auth.login.domain.usecase.LoginUseCase
import io.ktor.http.*
import io.ktor.http.cio.*
import kotlinx.coroutines.launch

data class MyUiState(
    val data: Response? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    var emailOrPhone by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    val numericRegex = Regex("[^0-9]")

    var phone by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var isPhoneSelected by mutableStateOf(true)
        private set

    fun login() {
        setEmailOrPhone()
        if (emailOrPhone.isEmpty() || password.isEmpty()) {
            errorMessage = "Please enter email or phone and password"
            return
        }
        isLoading = true
        viewModelScope.launch {
            val response = loginUseCase("emailOrPhone", "password")
            if (!response) {
                errorMessage = "Login failed"
            }
            isLoading = false
        }
    }

    private fun setEmailOrPhone() {
        if (isPhoneSelected) {
            emailOrPhone = phone
        }else{
            emailOrPhone = email
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
        if (phone.isNotEmpty()) {
            true
        } else {
            false
        }
    }

    fun updatePassword(input: String) {
        password = input
    }

    fun isPhoneSelected(bool: Boolean) {
        isPhoneSelected = bool
    }
}