package com.example.mobile_client_app.features.personalTraining.appointments.presntation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_client_app.common.component.CustomErrorDialog
import com.example.mobile_client_app.common.component.CustomMessageDialog
import com.example.mobile_client_app.common.component.FullScreenError
import com.example.mobile_client_app.common.component.FullScreenLoading
import com.example.mobile_client_app.common.component.LoadingOverlay
import com.example.mobile_client_app.features.personalTraining.appointments.domain.model.Appointment
import com.example.mobile_client_app.features.personalTraining.appointments.presntation.components.AppointmentCard
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.appointments
import mobile_client_app.composeapp.generated.resources.appointments_cancel_message
import mobile_client_app.composeapp.generated.resources.generic_error
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentsScreen(
    viewModel: AppointmentsViewModel = koinViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        FullScreenLoading()
    } else if (uiState.error != null) {
        FullScreenError(uiState.error!!, onRetry = {
            viewModel.onRefresh()
        })
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(Res.string.appointments)) },
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    items(Appointment.AppointmentStatus.entries) { filter ->
                        FilterChip(
                            selected = uiState.selectedFilter == filter,
                            onClick = { viewModel.onFilterChanged(filter) },
                            label = {
                                Text(
                                    text = stringResource(filter.displayName), fontSize = 14.sp
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.secondary,
                            )
                        )
                    }
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.filteredAppointments) { appointment ->
                        AppointmentCard(
                            appointment = appointment,
                            onCancel = { viewModel.showCancelWarringDialog(appointment.id) }
                        )
                    }
                }
            }
            if (viewModel.hasLoading.value) {
                LoadingOverlay()
            }
            CustomErrorDialog(
                showDialog = viewModel.errorDialogVisible.value,
                message = viewModel.errorDialogMessage.value ?: stringResource(Res.string.generic_error),
                onDismiss = { viewModel.changeErrorDialogVisible() },
            )
            CustomMessageDialog(
                showDialog = viewModel.cancelWarringDialogVisible.value,
                message = stringResource(Res.string.appointments_cancel_message),
                onConfirmClick = { viewModel.cancelAppointment(viewModel.cancelAppointmentID) },
                onCancelClick = { viewModel.clearCancelAppointment() }
            )
        }
    }
}