package com.example.mobile_client_app.auth.registering.presentaion.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.common.component.RoundedCornerButton
import com.example.mobile_client_app.common.component.RoundedCornerPasswordTextField
import com.example.mobile_client_app.common.component.RoundedCornerWithoutBackgroundTextField
import com.example.mobile_client_app.common.countryPicker.country
import mobile_client_app.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisteringScreen(
    viewModel: RegisteringViewModel = koinViewModel(),
    onNavigateToBackPage: () -> Unit,
    onNavigateToAdditionInformation: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()
    val snackbarHostState = remember { SnackbarHostState() }

    val event = viewModel.events.collectAsState().value

    LaunchedEffect(event) {
        if (event != null) {
            when (event) {
                is RegisteringEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                    viewModel.resetEvent()
                }

                RegisteringEvent.Reset -> {

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
                    if (viewModel.savePersonalInformation())
                        onNavigateToAdditionInformation()
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
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                    ) {
                        RoundedCornerWithoutBackgroundTextField(
                            value = viewModel.firstName,
                            onValueChange = { viewModel.updateFirstName(it) },
                            placeholder = stringResource(Res.string.first_name),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                        )
                        RoundedCornerWithoutBackgroundTextField(
                            value = viewModel.middleName,
                            onValueChange = { viewModel.updateMiddleName(it) },
                            placeholder = stringResource(Res.string.middle_name),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                        )
                    }
                    RoundedCornerWithoutBackgroundTextField(
                        value = viewModel.lastName,
                        onValueChange = { viewModel.updateLastName(it) },
                        placeholder = stringResource(Res.string.last_name),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                    )
                    RoundedCornerWithoutBackgroundTextField(
                        value = viewModel.idNumber,
                        onValueChange = { viewModel.updateIdNumber(it) },
                        placeholder = stringResource(Res.string.id_number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                    Card(
                        shape = RoundedCornerShape(25),
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                        onClick = {
                            viewModel.updateShowDatePicker(!viewModel.showDatePicker)
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (viewModel.getSelectDateAsString() != null) {
                                Text(
                                    viewModel.getSelectDateAsString()!!,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            } else {
                                Text(
                                    text = "DD/MM/YYYY",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }

                            IconButton(onClick = {
                                viewModel.updateShowDatePicker(!viewModel.showDatePicker)
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.CalendarToday,
                                    contentDescription = "Expand"
                                )
                            }
                        }
                    }
                    Card(
                        shape = RoundedCornerShape(25),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    viewModel.updateIsMale(true)
                                },
                                shape = RoundedCornerShape(30),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (viewModel.isMale) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.surfaceContainerHighest
                                ),
                                modifier = Modifier.weight(1f),
                            ) {
                                Text(
                                    text = stringResource(Res.string.male),
                                    color = if (viewModel.isMale) MaterialTheme.colorScheme.scrim else MaterialTheme.colorScheme.tertiary
                                )
                            }
                            Spacer(Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    viewModel.updateIsMale(false)
                                },
                                shape = RoundedCornerShape(30),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (!viewModel.isMale) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.surfaceContainerHighest
                                ),
                                modifier = Modifier.weight(1f),
                            ) {
                                Text(
                                    text = stringResource(Res.string.female),
                                    color = if (!viewModel.isMale) MaterialTheme.colorScheme.scrim else MaterialTheme.colorScheme.tertiary
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
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
                                if (viewModel.selectedCountry != null) {
                                    Row {
                                        Image(
                                            painter = painterResource(viewModel.selectedCountry!!.flag),
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        Text(
                                            stringResource(viewModel.selectedCountry!!.nameRes),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                } else {
                                    Text(
                                        text = stringResource(Res.string.selected_country),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                                IconButton(onClick = { viewModel.updateIsExpanded(isExpanded = !viewModel.isExpanded) }) {
                                    Icon(
                                        imageVector = if (viewModel.isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                        contentDescription = "Expand"
                                    )
                                }
                            }
                            DropdownMenu(
                                expanded = viewModel.isExpanded,
                                onDismissRequest = { viewModel.updateIsExpanded(false) },
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .clip(RoundedCornerShape(50))
                            ) {
                                country.forEach { country ->
                                    DropdownMenuItem(
                                        text = {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Row {
                                                    Image(
                                                        painter = painterResource(country.flag),
                                                        contentDescription = null,
                                                        modifier = Modifier.size(20.dp)
                                                    )
                                                    Spacer(Modifier.width(8.dp))
                                                    Text(
                                                        stringResource(country.nameRes),
                                                        style = MaterialTheme.typography.bodySmall
                                                    )
                                                }
                                                Text(country.code, style = MaterialTheme.typography.bodyMedium)
                                            }
                                        },
                                        onClick = {
                                            viewModel.updateCountry(country)
                                            viewModel.updateIsExpanded(isExpanded = false)
                                        }
                                    )
                                }
                            }
                        }
                    }
                    OutlinedTextField(
                        enabled = viewModel.selectedCountry != null,
                        value = viewModel.phoneNumber,
                        placeholder = { Text(stringResource(Res.string.phone)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clip(RoundedCornerShape(25)),
                        shape = RoundedCornerShape(25),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        prefix = {
                            Row {
                                if (viewModel.selectedCountry == null) {
                                    Text("+", style = MaterialTheme.typography.bodyLarge)
                                } else {
                                    Text(viewModel.selectedCountry!!.code, style = MaterialTheme.typography.bodyLarge)
                                }
                                Spacer(Modifier.width(8.dp))
                                Text("|", style = MaterialTheme.typography.bodyMedium)
                                Spacer(Modifier.width(8.dp))
                            }
                        },
                        maxLines = 1,
                        onValueChange = { viewModel.updatePhoneNumber(it) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                        ),
                    )
                    RoundedCornerWithoutBackgroundTextField(
                        value = viewModel.email,
                        onValueChange = { viewModel.updateEmail(it) },
                        placeholder = stringResource(Res.string.email),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                            .clip(RoundedCornerShape(25)),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    )
                    RoundedCornerPasswordTextField(
                        value = viewModel.password,
                        onValueChange = { viewModel.updatePassword(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clip(RoundedCornerShape(25)),
                        placeholder = stringResource(Res.string.password),
                        isPasswordVisible = viewModel.isPasswordVisible,
                        onChangeVisibility = {
                            viewModel.updatePasswordVisibility(!viewModel.isPasswordVisible)
                        }
                    )
                    Text(
                        text = "Password Strength",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                    )
                    LinearProgressIndicator(
                        gapSize = 25.dp,
                        progress = { viewModel.passwordStrength },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                        color = MaterialTheme.colorScheme.scrim,
                    )

                    Text(
                        text = when {
                            viewModel.passwordStrength < 0.33f -> stringResource(Res.string.weak)
                            viewModel.passwordStrength < 0.66f -> stringResource(Res.string.medium)
                            else -> stringResource(Res.string.strong)
                        },
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                    )
                }
            }
            if (viewModel.showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { viewModel.updateShowDatePicker(false) },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.updateDateOfBirth(datePickerState.selectedDateMillis ?: 0)
                                viewModel.updateShowDatePicker(false)
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { viewModel.updateShowDatePicker(false) }
                        ) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
        }
    }
}