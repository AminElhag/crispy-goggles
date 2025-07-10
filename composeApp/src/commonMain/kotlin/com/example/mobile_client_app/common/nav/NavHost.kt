package com.example.mobile_client_app.common.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobile_client_app.features.auth.login.presentation.ui.LoginScreen
import com.example.mobile_client_app.features.auth.registering.presentaion.ui.AdditionInformationScreen
import com.example.mobile_client_app.features.auth.registering.presentaion.ui.RegisteringScreen
import com.example.mobile_client_app.features.membership.main.presentation.MembershipScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Login.route
    ) {
        composable(AppScreen.Login.route) {
            LoginScreen(onNavigateToRegisteringScreen = {
                navController.navigate(AppScreen.Registering.route)
            })
        }
        composable(AppScreen.Registering.route){
            RegisteringScreen(
                onNavigateToBackPage = {
                    navController.popBackStack()
                },
                onNavigateToAdditionInformation = {
                    navController.navigate(AppScreen.AdditionInformation.route)
                }
            )
        }
        composable(AppScreen.AdditionInformation.route) {
            AdditionInformationScreen(
                onNavigateToBackPage = {
                    navController.popBackStack()
                },
                onCreateUserSuccess = {
                    navController.navigate(AppScreen.Membership.route)
                }
            )
        }
        composable(AppScreen.Membership.route) {
            MembershipScreen(
                onNavigateToBackPage = {

                }, onContinue = {

                }
            )
        }
    }
}