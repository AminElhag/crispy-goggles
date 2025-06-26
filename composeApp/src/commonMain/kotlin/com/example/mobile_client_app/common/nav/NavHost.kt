package com.example.mobile_client_app.common.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobile_client_app.DetailScreen
import com.example.mobile_client_app.auth.login.presentation.ui.LoginScreen
import com.example.mobile_client_app.auth.registering.presentaion.ui.RegisteringScreen

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

                }
            )
        }

        /*composable(AppScreen.Login.route) {
            DetailScreen(onNavigateBack = { navController.popBackStack() })
        }*/
    }
}