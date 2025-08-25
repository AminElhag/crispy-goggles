package com.example.mobile_client_app.common.nav

sealed class AppScreen(val route: String) {
    object Login : AppScreen("login")
    object Registering : AppScreen("registering")
    object AdditionInformation : AppScreen("addition_information")
    object Membership : AppScreen("membership")
    object Payments : AppScreen("payments")
    object OnBoarding : AppScreen("onboarding")
    object Notification : AppScreen("notification")
    object AddAppointment : AppScreen("add_appointment")
    object ClassDetail : AppScreen("detail_class")
}