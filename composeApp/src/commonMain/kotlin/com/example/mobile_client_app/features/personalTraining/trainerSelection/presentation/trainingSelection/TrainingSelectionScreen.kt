package com.example.mobile_client_app.features.personalTraining.trainerSelection.presentation.trainingSelection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.common.component.CustomErrorDialog
import com.example.mobile_client_app.common.component.CustomMessageDialog
import com.example.mobile_client_app.common.component.DateRangePicker
import com.example.mobile_client_app.common.component.ErrorBx
import com.example.mobile_client_app.common.component.FullScreenError
import com.example.mobile_client_app.common.component.FullScreenLoading
import com.example.mobile_client_app.common.component.RoundedCornerButton
import com.example.mobile_client_app.common.component.SimpleLoadingOverlay
import com.example.mobile_client_app.common.component.TimeSlotButton
import mobile_client_app.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import saschpe.log4k.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingSelectionScreen(
    viewModel: TrainingSelectionViewModel = koinViewModel(),
    selectedTrainerId: String,
    onBackClick: () -> Unit,
    onConfirmClick:()->Unit,
) {

    LaunchedEffect(Unit) {
        viewModel.setTrainerId(selectedTrainerId)
    }

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
                CenterAlignedTopAppBar(title = { Text(stringResource(Res.string.book_a_session)) }, navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                })
            }) { innerPadding ->
            Box {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(
                                    Res.string.step_number_of, 2, 3
                                ),
                                style = MaterialTheme.typography.labelSmall,
                            )
                        }
                        Box(
                            modifier = Modifier.fillMaxWidth().height(8.dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.scrim,
                                    shape = RoundedCornerShape(12.dp)
                                ).background(
                                    MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f),
                                    RoundedCornerShape(4.dp)
                                ),
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth(0.66f).fillMaxHeight()
                                    .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(4.dp))
                            )
                        }
                        Text(
                            text = stringResource(Res.string.select_training_type),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        )

                        Card(
                            Modifier.fillMaxWidth().padding(vertical = 16.dp), shape = RoundedCornerShape(25)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                viewModel.trainingTypes.forEach { type ->
                                    Button(
                                        onClick = {
                                            viewModel.updateSelectType(type)
                                        },
                                        shape = RoundedCornerShape(30),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (viewModel.hasSelectedType(type.id)) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.surfaceContainerHighest
                                        ),
                                        modifier = Modifier.weight(1f),
                                    ) {
                                        Text(
                                            text = type.displayName,
                                            color = if (viewModel.hasSelectedType(type.id)) MaterialTheme.colorScheme.scrim else MaterialTheme.colorScheme.tertiary
                                        )
                                    }
                                }
                            }
                        }
                        Box {
                            Row(
                                modifier = Modifier.fillMaxWidth().border(
                                    width = 1.dp,
                                    color = if (viewModel.isTrainingSpecializationDropdownExpanded.value) MaterialTheme.colorScheme.scrim else MaterialTheme.colorScheme.tertiary,
                                    shape = RoundedCornerShape(12.dp)
                                ).background(
                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                    shape = RoundedCornerShape(12.dp)
                                ).clickable(enabled = !uiState.isLoading && uiState.error == null) {
                                    viewModel.changeTrainingSpecializationDropdownExpanded(!viewModel.isTrainingSpecializationDropdownExpanded.value)
                                }.padding(16.dp), verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = viewModel.selectedSpecializationType.value?.name
                                        ?: stringResource(Res.string.training_specialization),
                                    modifier = Modifier.weight(1f),
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Dropdown arrow",
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier.rotate(if (viewModel.isTrainingSpecializationDropdownExpanded.value) 180f else 0f)
                                )

                                /*if (uiState.isLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        strokeWidth = 2.dp,
                                        color = Color.Gray
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Dropdown arrow",
                                        tint = Color.Gray,
                                        modifier = Modifier.rotate(if (viewModel.isTrainingSpecializationDropdownExpanded.value) 180f else 0f)
                                    )
                                }*/
                            }
                        }

                        DropdownMenu(
                            expanded = viewModel.isTrainingSpecializationDropdownExpanded.value,
                            onDismissRequest = {
                                viewModel.changeTrainingSpecializationDropdownExpanded(false)
                            },
                            modifier = Modifier.fillMaxWidth(0.9f)
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                        ) {
                            uiState.list.forEach { item ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = item.name,
                                            color = if (item.isAvailable) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSecondaryContainer.copy(
                                                alpha = 0.3f
                                            ),
                                        )
                                    },
                                    onClick = {
                                        viewModel.updateSpecializationType(item)
                                        viewModel.changeTrainingSpecializationDropdownExpanded(false)
                                    },
                                    enabled = item.isAvailable,
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = stringResource(Res.string.choose_a_date),
                            style = MaterialTheme.typography.displaySmall,
                        )

                        Box(
                            modifier = Modifier.fillMaxWidth().height(320.dp)
                        ) {
                            DateRangePicker(
                                currentMonth = viewModel.currentMonth.value,
                                onPreviousMonthChange = { viewModel.onPreviousMonthChange() },
                                onNextMonthChange = { viewModel.onNextMonthChange() },
                                startDate = viewModel.getSelectedDate(),
                                onStartDateSelected = {
                                    viewModel.updateSelectedDate(it)
                                },
                                onEndDateSelected = {}, // Not used anymore
                                config = viewModel.dateRangeConfig(),
                                onErrorMessage = {
                                    viewModel.errorMessage.value = it
                                },
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (uiState.isGettingAvailableTime) {
                            SimpleLoadingOverlay()
                        }

                        if (uiState.isGettingAvailableError != null) {
                            Box {
                                ErrorBx(
                                    errorMessage = uiState.isGettingAvailableError!!,
                                ) {
                                    viewModel.onGetTimeSlot()
                                }
                            }
                        }

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            maxItemsInEachRow = 3,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            uiState.availableTimes.forEach { time ->
                                Log.info { "${time.title}: ${time.isAvailable}" }
                                TimeSlotButton(
                                    time = time.title,
                                    isSelected = viewModel.isTimeSlotSelected(time),
                                    isDisabled = !(time.isAvailable),
                                    onClick = { viewModel.updateSelectedTime(time) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        RoundedCornerButton(
                            onClick = {
                                viewModel.onRequestAppointment()
                            },
                            text = stringResource(Res.string.next),
                            modifier = Modifier.fillMaxWidth().height(50.dp).clip(RoundedCornerShape(25)),
                            enabled = viewModel.isNextButtonEnable()
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        CustomErrorDialog(
                            showDialog = uiState.requestingAppointmentError != null,
                            message = uiState.requestingAppointmentError?: stringResource(Res.string.generic_error),
                            onDismiss = {
                                viewModel.onRequestAppointment()
                            },
                            onRetryMessage = Res.string.retry
                        )

                        CustomMessageDialog(
                            showDialog = uiState.isRequestingAppointmentComparable,
                            title = Res.string.successful,
                            message = stringResource(Res.string.appointment_successful_message),
                            onConfirmClick = {
                                onConfirmClick()
                            },
                        )
                    }
                }
                if (uiState.isRequestingAppointment){
                    SimpleLoadingOverlay()
                }
            }
        }
    }
}