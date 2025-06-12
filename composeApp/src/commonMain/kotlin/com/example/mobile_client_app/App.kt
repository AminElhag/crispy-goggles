package com.example.mobile_client_app

import androidx.compose.runtime.Composable
import com.example.mobile_client_app.auth.login.presentation.ui.LoginScreen
import com.example.mobile_client_app.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme {
        LoginScreen()
    }
}