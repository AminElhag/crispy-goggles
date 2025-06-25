package com.example.mobile_client_app.common.nav

sealed class AppScreen(val route: String) {
    object Login : AppScreen("login")
    object Registering : AppScreen("registering")
}