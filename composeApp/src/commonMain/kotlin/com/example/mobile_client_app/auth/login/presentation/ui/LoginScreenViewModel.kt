package com.example.mobile_client_app.auth.login.presentation.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_client_app.auth.login.domain.usecase.LoginUseCase
import io.ktor.http.cio.Response
import io.ktor.http.isSuccess
import kotlinx.coroutines.launch

data class MyUiState(
    val data: Response? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

class LoginScreenViewModel (
    private val loginUseCase: LoginUseCase
): ViewModel() {
    var emailOrPhone by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun login() {
        if (emailOrPhone.isEmpty() || password.isEmpty()) {
            errorMessage = "Please enter email or phone and password"
            return
        }
        isLoading = true
        viewModelScope.launch {
            val response = loginUseCase(emailOrPhone, password)
            if (!response.status.isSuccess()) {
                errorMessage = "Login failed"
            }
            isLoading = false
        }
    }
}