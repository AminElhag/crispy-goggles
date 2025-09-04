package com.example.mobile_client_app.common.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mobile_client_app.common.rememberTokenState
import com.example.mobile_client_app.features.auth.login.presentation.ui.LoginScreen
import com.example.mobile_client_app.features.auth.registering.presentaion.RegistrationDataHolder
import com.example.mobile_client_app.features.auth.registering.presentaion.additionInformation.AdditionInformationScreen
import com.example.mobile_client_app.features.auth.registering.presentaion.additionInformation.AdditionalInfoViewModel
import com.example.mobile_client_app.features.auth.registering.presentaion.registering.RegisteringScreen
import com.example.mobile_client_app.features.classes.bookingClass.presentation.BookingClassScreen
import com.example.mobile_client_app.features.membership.main.domain.model.CheckoutInitResponse
import com.example.mobile_client_app.features.membership.main.presentation.MembershipScreen
import com.example.mobile_client_app.features.membership.payment.presentation.PaymentScreen
import com.example.mobile_client_app.features.notifications.presntation.list.NotificationsListScreen
import com.example.mobile_client_app.features.onboarding.OnBoardingScreen
import com.example.mobile_client_app.features.personalTraining.trainerSelection.presentation.trainerSelection.TrainerSelectionScreen
import com.example.mobile_client_app.features.personalTraining.trainerSelection.presentation.trainingSelection.TrainingSelectionScreen
import kotlinx.serialization.json.Json
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {

    val dataStore: DataStore<Preferences> = koinInject()
    val tokenState = rememberTokenState(dataStore)

    NavHost(
        navController = navController,
        startDestination = if (tokenState.hasToken) AppScreen.OnBoarding.route else AppScreen.Login.route
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
        /*composable(AppScreen.Registering.route) {
            RegisteringScreen(
                onNavigateToBackPage = {
                    navController.popBackStack()
                },
                onNavigateToAdditionInformation = {
                    navController.navigate(AppScreen.AdditionInformation.route)
                }
            )
        }*/
        /*composable(AppScreen.Registering.route) {
            RegisteringScreen(
                onNavigateToBackPage = {
                    navController.popBackStack()
                },
                onNavigateToAdditionInformation = { personalInfoData ->
                    RegistrationDataHolder.setPersonalInfoData(personalInfoData)
                    navController.navigate(AppScreen.AdditionInformation.route)
                }
            )
        }
        composable(AppScreen.AdditionInformation.route) {
            val additionalInfoViewModel: AdditionalInfoViewModel = koinViewModel()
            LaunchedEffect(Unit) {
                RegistrationDataHolder.getPersonalInfoData()?.let { personalData ->
                    additionalInfoViewModel.setPersonalInfoData(personalData)
                }
            }
            AdditionInformationScreen(
                viewModel = additionalInfoViewModel,
                onNavigateToBackPage = {
                    navController.popBackStack()
                },
                onCreateUserSuccess = {
                    RegistrationDataHolder.clearData()
                    navController.navigate(AppScreen.Membership.route)
                }
            )
        }*/
        /*composable(AppScreen.AdditionInformation.route) {
            AdditionInformationScreen(
                onNavigateToBackPage = {
                    navController.popBackStack()
                },
                onCreateUserSuccess = {
                    navController.navigate(AppScreen.Membership.route)
                }
            )
        }*/
        composable(AppScreen.Registering.route) {
            val dataHolder: RegistrationDataHolder = koinInject()
            RegisteringScreen(
                onNavigateToBackPage = {
                    navController.popBackStack()
                },
                onNavigateToAdditionInformation = { personalInfoData ->
                    dataHolder.setPersonalInfoData(personalInfoData)
                    navController.navigate(AppScreen.AdditionInformation.route)
                }
            )
        }

        composable(AppScreen.AdditionInformation.route) {
            val additionalInfoViewModel: AdditionalInfoViewModel = koinViewModel()
            val dataHolder: RegistrationDataHolder = koinInject()

            LaunchedEffect(Unit) {
                dataHolder.getPersonalInfoData()?.let { personalData ->
                    additionalInfoViewModel.setPersonalInfoData(personalData)
                }
            }

            AdditionInformationScreen(
                viewModel = additionalInfoViewModel,
                onNavigateToBackPage = {
                    navController.popBackStack()
                },
                onCreateUserSuccess = {
                    dataHolder.clearData()
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
                    navController.navigate(AppScreen.ClassDetail.route + "/$it")
                },
                onAddAppointmentClick = {
                    navController.navigate(AppScreen.AddAppointment.route)
                },
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
        composable(AppScreen.ClassDetail.route + "/{classId}") { backStackEntry ->
            val classId = backStackEntry.savedStateHandle.get<String>("classId")?.toIntOrNull() ?: return@composable
            BookingClassScreen(
                classId = classId,
                onCancelBookingClick = {
                    navController.popBackStack()
                },
                onConfirmClick = {
                    navController.popBackStack()
                }
            )
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