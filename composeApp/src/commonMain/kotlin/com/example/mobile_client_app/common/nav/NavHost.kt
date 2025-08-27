package com.example.mobile_client_app.common.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mobile_client_app.features.auth.login.presentation.ui.LoginScreen
import com.example.mobile_client_app.features.auth.registering.presentaion.ui.AdditionInformationScreen
import com.example.mobile_client_app.features.auth.registering.presentaion.ui.RegisteringScreen
import com.example.mobile_client_app.features.membership.main.domain.model.CheckoutInitResponse
import com.example.mobile_client_app.features.membership.main.presentation.MembershipScreen
import com.example.mobile_client_app.features.membership.payment.presentation.PaymentScreen
import com.example.mobile_client_app.features.notifications.presntation.list.NotificationsListScreen
import com.example.mobile_client_app.features.onboarding.OnBoardingScreen
import com.example.mobile_client_app.features.personalTraining.trainerSelection.presentation.trainerSelection.TrainerSelectionScreen
import com.example.mobile_client_app.features.personalTraining.trainerSelection.presentation.trainingSelection.TrainingSelectionScreen
import kotlinx.serialization.json.Json

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.OnBoarding.route
    ) {
        composable(AppScreen.Login.route) {
            LoginScreen(
                onNavigateToRegisteringScreen = {
                    navController.navigate(AppScreen.Registering.route)
                },
                onLoginComplete = {
                    navController.navigate(AppScreen.OnBoarding.route)
                }
            )
        }
        composable(AppScreen.Registering.route) {
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

                },
                onContinue = { checkoutInitResponse ->
                    val json = Json.encodeToString(checkoutInitResponse)
                    /*navController.navigate(AppScreen.Payments.route + "/${json.encodeToRoute()}")*/
                    navController.navigate(AppScreen.Payments.route + "/$json")
                }
            )
        }
        composable(
            AppScreen.Payments.route + "/{json}",
        ) { backStackEntry ->
            val response = backStackEntry.savedStateHandle.get<String>("json")
            if (response != null) {
                val checkoutResponse = Json.decodeFromString<CheckoutInitResponse>(response)
                PaymentScreen(
                    data = checkoutResponse,
                ) {
                    navController.navigate(AppScreen.OnBoarding.route)
                }
            }
        }
        composable(AppScreen.OnBoarding.route) {
            OnBoardingScreen(
                onNotificationsClick = { navController.navigate(AppScreen.Notification.route) },
                onLogoutTrigger = {
                    navController.navigate(AppScreen.Login.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                onClassClick = {
                    navController.navigate(AppScreen.ClassDetail.route)
                },
                onAddAppointmentClick = {
                    navController.navigate(AppScreen.AddAppointment.route)
                }
            )
        }
        composable(AppScreen.Notification.route) {
            NotificationsListScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(AppScreen.AddAppointment.route) {
            TrainerSelectionScreen(
                onCancelBookingClick = {
                    navController.popBackStack()
                },
                onNextClick = { selectedTrainerId ->
                    navController.navigate(AppScreen.TrainerSelection.route + "/$selectedTrainerId")
                }
            )
        }
        composable(AppScreen.ClassDetail.route) {

        }

        composable(
            route = AppScreen.TrainerSelection.route + "/{trainerId}",
            arguments = listOf(
                navArgument("trainerId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val selectedTrainerId = backStackEntry.savedStateHandle.get<String>("trainerId") ?: return@composable
            TrainingSelectionScreen(
                selectedTrainerId = selectedTrainerId,
                onBackClick = {
                    navController.popBackStack()
                },
                onConfirmClick = {
                    navController.navigate(AppScreen.OnBoarding.route)
                }
            )
        }
    }
}