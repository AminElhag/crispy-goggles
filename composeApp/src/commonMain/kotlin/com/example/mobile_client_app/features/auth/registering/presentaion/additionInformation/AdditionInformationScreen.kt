package com.example.mobile_client_app.features.auth.registering.presentaion.additionInformation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.common.component.RoundedCornerButton
import com.example.mobile_client_app.common.component.RoundedCornerWithoutBackgroundTextField
import com.example.mobile_client_app.common.hearAboutUs
import com.example.mobile_client_app.common.medicalCondition
import mobile_client_app.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/*
@Composable
fun AdditionInformationScreen(
    viewModel: AdditionalInfoViewModel = koinViewModel(),
    onNavigateToBackPage: () -> Unit,
    onCreateUserSuccess: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val event = viewModel.events.collectAsState().value


    LaunchedEffect(event) {
        if (event != null) {
            when (event) {
                is AdditionalInfoEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                    viewModel.resetEvent()
                }

                AdditionalInfoEvent.Reset -> {

                }

                is AdditionalInfoEvent.ResponseSuccess -> {
                    onCreateUserSuccess()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            (CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Personal Information",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateToBackPage) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back Button")
                    }
                },
            ))
        },
        bottomBar = {
            RoundedCornerButton(
                onClick = {
                    viewModel.createUser()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(50)),
                text = stringResource(Res.string.next)
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues),
            ) {
                RoundedCornerWithoutBackgroundTextField(
                    value = viewModel.emergencyContact,
                    onValueChange = { viewModel.updateEmergencyContact(it) },
                    placeholder = stringResource(Res.string.emergency_contact),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Card(
                        shape = RoundedCornerShape(25),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (viewModel.hearAboutUs != null) {
                                Row {
                                    Text(
                                        stringResource(viewModel.hearAboutUs!!.name),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            } else {
                                Text(
                                    text = stringResource(Res.string.how_did_you_hear_about_us),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            IconButton(onClick = { viewModel.updateIsHearAboutUsSelectorExpanded(!viewModel.isHearAboutUsSelectorExpanded) }) {
                                Icon(
                                    imageVector = if (viewModel.isHearAboutUsSelectorExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Expand"
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = viewModel.isHearAboutUsSelectorExpanded,
                            onDismissRequest = { viewModel.updateIsHearAboutUsSelectorExpanded(false) },
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                        ) {
                            hearAboutUs.forEach { it ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            stringResource(it.name),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    },
                                    onClick = {
                                        viewModel.updateHearAboutUs(it)
                                        viewModel.updateIsHearAboutUsSelectorExpanded(false)
                                    }
                                )
                            }
                        }
                    }
                }

                RoundedCornerWithoutBackgroundTextField(
                    value = viewModel.occupation,
                    onValueChange = { viewModel.updateOccupation(it) },
                    placeholder = stringResource(Res.string.occupation),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Card(
                        shape = RoundedCornerShape(25),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                viewModel.getMedicalConditionsAsString(),
                                style = MaterialTheme.typography.bodySmall,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.weight(4f)
                            )
                            Spacer(Modifier.width(8.dp).weight(1f))
                            IconButton(
                                onClick = { viewModel.updateIsMedicalConditionExpanded(!viewModel.isMedicalConditionExpanded) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = if (viewModel.isMedicalConditionExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Expand"
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = viewModel.isMedicalConditionExpanded,
                            onDismissRequest = { viewModel.updateIsMedicalConditionExpanded(false) },
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                        ) {
                            medicalCondition.forEach { checked ->
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                stringResource(checked.name),
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                            Checkbox(
                                                checked = viewModel.isMedicalConditionIsSelected(checked.id),
                                                onCheckedChange = {
                                                    if (viewModel.isMedicalConditionIsSelected(checked.id)) {
                                                        viewModel.removeMedicalCondition(checked)
                                                    } else {
                                                        viewModel.addMedicalCondition(checked)
                                                    }
                                                })
                                        }
                                    },
                                    onClick = {
                                        */
/*viewModel.updateHearAboutUs(it)
                                        viewModel.updateIsHearAboutUsSelectorExpanded(false)*//*

                                    }
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdditionInformationScreen(
    viewModel: AdditionalInfoViewModel = koinViewModel(),
    onNavigateToBackPage: () -> Unit,
    onCreateUserSuccess: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val event = viewModel.events.collectAsState().value

    LaunchedEffect(event) {
        if (event != null) {
            when (event) {
                is AdditionalInfoEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                    viewModel.resetEvent()
                }

                AdditionalInfoEvent.Reset -> {
                    // Handle reset if needed
                }

                is AdditionalInfoEvent.ResponseSuccess -> {
                    onCreateUserSuccess()
                    viewModel.resetEvent()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Additional Information",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateToBackPage) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back Button")
                    }
                },
            )
        },
        bottomBar = {
            RoundedCornerButton(
                onClick = {
                    viewModel.createUser()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(50)),
                text = stringResource(Res.string.next)
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues),
            ) {
                RoundedCornerWithoutBackgroundTextField(
                    value = viewModel.emergencyContact,
                    onValueChange = { viewModel.updateEmergencyContact(it) },
                    placeholder = stringResource(Res.string.emergency_contact),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                )

                RoundedCornerWithoutBackgroundTextField(
                    value = viewModel.occupation,
                    onValueChange = { viewModel.updateOccupation(it) },
                    placeholder = stringResource(Res.string.occupation),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Card(
                        shape = RoundedCornerShape(25),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (viewModel.hearAboutUs != null) {
                                Row {
                                    Text(
                                        stringResource(viewModel.hearAboutUs!!.name),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            } else {
                                Text(
                                    text = stringResource(Res.string.how_did_you_hear_about_us),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            IconButton(onClick = { viewModel.updateIsHearAboutUsSelectorExpanded(!viewModel.isHearAboutUsSelectorExpanded) }) {
                                Icon(
                                    imageVector = if (viewModel.isHearAboutUsSelectorExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Expand"
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = viewModel.isHearAboutUsSelectorExpanded,
                            onDismissRequest = { viewModel.updateIsHearAboutUsSelectorExpanded(false) },
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                        ) {
                            hearAboutUs.forEach { it ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            stringResource(it.name),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    },
                                    onClick = {
                                        viewModel.updateHearAboutUs(it)
                                        viewModel.updateIsHearAboutUsSelectorExpanded(false)
                                    }
                                )
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Card(
                        shape = RoundedCornerShape(25),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                viewModel.getMedicalConditionsAsString(),
                                style = MaterialTheme.typography.bodySmall,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.weight(4f)
                            )
                            Spacer(Modifier.width(8.dp).weight(1f))
                            IconButton(
                                onClick = { viewModel.updateIsMedicalConditionExpanded(!viewModel.isMedicalConditionExpanded) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = if (viewModel.isMedicalConditionExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Expand"
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = viewModel.isMedicalConditionExpanded,
                            onDismissRequest = { viewModel.updateIsMedicalConditionExpanded(false) },
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                        ) {
                            medicalCondition.forEach { checked ->
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                stringResource(checked.name),
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                            Checkbox(
                                                checked = viewModel.isMedicalConditionIsSelected(checked.id),
                                                onCheckedChange = {
                                                    if (viewModel.isMedicalConditionIsSelected(checked.id)) {
                                                        viewModel.removeMedicalCondition(checked)
                                                    } else {
                                                        viewModel.addMedicalCondition(checked)
                                                    }
                                                })
                                        }
                                    },
                                    onClick = {
                                        // Handle click if needed
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}