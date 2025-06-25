package com.example.mobile_client_app

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class AppScreen(val route: String) {
    object Home : AppScreen("home")
    object Detail : AppScreen("detail")
}

@Composable
fun HomeScreen(onNavigate: () -> Unit) {
    Column {
        Text("Home Screen")
        Button(onClick = onNavigate) {
            Text("Go to Detail")
        }
    }
}

@Composable
fun DetailScreen(onNavigateBack: () -> Unit) {
    Column {
        Text("Detail Screen")
        Button(onClick = onNavigateBack) {
            Text("Back to Home")
        }
    }
}

/*
@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Home.route
    ) {
        composable(AppScreen.Home.route) {
            HomeScreen(onNavigate = { navController.navigate(AppScreen.Detail.route) })
        }
        composable(AppScreen.Detail.route) {
            DetailScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}*/
