package com.example.mobile_client_app.features.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.features.auth.profile.presentation.ProfileScreen
import com.example.mobile_client_app.features.onboarding.home.presntation.HomeScreen

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    object QrCode : Screen("qr_code", "QrCode", Icons.Default.QrCode)
    object Classes : Screen("classes", "Classes", Icons.Default.Class)
    object Store : Screen("store", "Store", Icons.Default.Store)
}

@Composable
fun OnBoardingScreen(
    onNotificationsClick: () -> Unit,
    onLogoutTrigger: () -> Unit,
) {
    var selectedScreen by remember { mutableStateOf(Screen.Home.route) }

    Scaffold(
        bottomBar = {
            Column {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)
                )
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.background,
                ) {
                    val screens = listOf(Screen.Profile, Screen.QrCode, Screen.Home, Screen.Store, Screen.Classes)
                    screens.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    screen.icon, contentDescription = screen.title,
                                    tint = if (selectedScreen == screen.route) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            },
                            selected = selectedScreen == screen.route,
                            onClick = { selectedScreen = screen.route },
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(bottom = paddingValues.calculateBottomPadding()),
            contentAlignment = Alignment.Center
        ) {
            when (selectedScreen) {
                Screen.Profile.route -> {
                    ProfileScreen(
                        onLogoutTrigger = onLogoutTrigger,
                    )
                }

                Screen.QrCode.route -> {
                    Text(text = "Qr Code", style = MaterialTheme.typography.bodyLarge)
                }

                Screen.Home.route -> {
                    HomeScreen(
                        onNotificationsClick = {
                            onNotificationsClick()
                        },
                        onBannerClick = {

                        },
                        onViewClassClick = {

                        }
                    )
                }

                Screen.Store.route -> {
                    Text(text = "Store", style = MaterialTheme.typography.bodyLarge)
                }

                Screen.Classes.route -> {
                    Text(text = "Classes", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}