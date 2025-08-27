package com.example.mobile_client_app.features.auth.profile.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.common.component.DateRangePickerDialog
import com.example.mobile_client_app.common.component.FullScreenError
import com.example.mobile_client_app.common.component.FullScreenLoading
import com.example.mobile_client_app.common.component.LoadingOverlay
import com.example.mobile_client_app.common.models.DateRangePickerConfig
import com.example.mobile_client_app.features.auth.profile.presentation.components.CurrentStatusSection
import com.example.mobile_client_app.features.auth.profile.presentation.components.FreezeMembershipButton
import com.example.mobile_client_app.features.auth.profile.presentation.components.HealthStatusSection
import com.example.mobile_client_app.features.auth.profile.presentation.components.ProfileSection
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.profile
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    onLogoutTrigger: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val event = viewModel.event.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(event) {
        when (event) {
            ProfileScreenUiEvent.Refresh -> TODO()
            ProfileScreenUiEvent.Reset -> viewModel.resetEvent()
            is ProfileScreenUiEvent.ShowSnackbar -> {
                snackbarHostState.showSnackbar(event.message)
                viewModel.resetEvent()
            }

            ProfileScreenUiEvent.Logout -> {
                onLogoutTrigger()
            }
        }
    }
    if (uiState.isLoading) {
        FullScreenLoading()
    } else if (uiState.error != null) {
        FullScreenError(uiState.error!!, onRetry = {
            viewModel.refresh()
        })
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(Res.string.profile)) },
                    actions = {
                        IconButton(onClick = { viewModel.deleteLoginInformation() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Logout,
                                contentDescription = "Profile"
                            )
                        }
                    }
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { innerPadding ->
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth().padding(innerPadding)
                    .padding(bottom = 0.dp)
            ) {
                ProfileSection(
                    name = uiState.data?.name ?: "",
                    memberSince = uiState.data?.memberSince ?: "",
                    membershipStatus = stringResource(viewModel.memberStatus(uiState.data!!.membershipStatus)),
                )
                Spacer(Modifier.height(16.dp))
                CurrentStatusSection(
                    statusMetrics = uiState.data?.statusMetrics!!,
                    cardBackgroundColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                )
                Spacer(Modifier.height(16.dp))
                FreezeMembershipButton(
                    enabled = uiState.data!!.canFreezeMembership,
                    onClick = { viewModel.setShowFreezingDialog(true) }
                )
                Spacer(Modifier.height(16.dp))
                HealthStatusSection(
                    healthMetrics = uiState.data!!.healthMetrics,
                    getChangeColor = viewModel::getChangeColor
                )
            }
            if (viewModel.getShowFreezingDialog()) {
                DateRangePickerDialog(
                    onDismiss = {
                        viewModel.onFreezingSend()
                    },
                    onDateRangeSelected = { dateRange ->
                        viewModel.setDateRange(dateRange)
                    },
                    initialStartDate = viewModel.getStartDate(),
                    initialEndDate = viewModel.getEndDate(),
                    config = DateRangePickerConfig(
                        allowPastDates = false,
                        maxRangeDays = 5,
                        minRangeDays = 2,
                    )
                )
            }
            if (viewModel.getShowLoadingDialog()) {
                LoadingOverlay()
            }
        }
    }
}
