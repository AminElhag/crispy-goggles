package com.example.mobile_client_app.auth.login.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.common.CountryPicker.Country
import com.example.mobile_client_app.common.CountryPicker.country
import mobile_client_app.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel()
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf<Country?>(null) }

    val showSnackbar by remember { mutableStateOf(viewModel.showSnackbar) }
    val snackbarMessage by remember { mutableStateOf(viewModel.snackbarMessage) }
    val snackbarHostState = remember { SnackbarHostState() }

    val event = viewModel.events.collectAsState().value

    LaunchedEffect(event) {
        if (event != null) {
            when (event) {
                is LoginEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Box {
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Image(
                        painter = painterResource(Res.drawable.login_main_image),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = stringResource(Res.string.welcome_back),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                    Card(
                        shape = RoundedCornerShape(25),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    viewModel.isPhoneSelected(true)
                                },
                                shape = RoundedCornerShape(30),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (viewModel.isPhoneSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.surfaceContainerHighest
                                ),
                                modifier = Modifier.weight(1f),
                            ) {
                                Text(
                                    text = stringResource(Res.string.phone),
                                    color = if (viewModel.isPhoneSelected) MaterialTheme.colorScheme.scrim else MaterialTheme.colorScheme.tertiary
                                )
                            }
                            Spacer(Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    viewModel.updatePassword("")
                                    viewModel.isPhoneSelected(false)
                                },
                                shape = RoundedCornerShape(30),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (!viewModel.isPhoneSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.surfaceContainerHighest
                                ),
                                modifier = Modifier.weight(1f),
                            ) {
                                Text(
                                    text = stringResource(Res.string.email),
                                    color = if (!viewModel.isPhoneSelected) MaterialTheme.colorScheme.scrim else MaterialTheme.colorScheme.tertiary
                                )
                            }
                        }
                    }
                    if (viewModel.isPhoneSelected) {
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
                                    if (selectedCountry != null) {
                                        Row {
                                            Image(
                                                painter = painterResource(selectedCountry!!.flag),
                                                contentDescription = null,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(Modifier.width(8.dp))
                                            Text(
                                                stringResource(selectedCountry!!.nameRes),
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    } else {
                                        Text(
                                            text = stringResource(Res.string.selected_country),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }

                                    IconButton(onClick = { isExpanded = !isExpanded }) {
                                        Icon(
                                            imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                            contentDescription = "Expand"
                                        )
                                    }
                                }
                                DropdownMenu(
                                    expanded = isExpanded,
                                    onDismissRequest = { isExpanded = false },
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
                                                selectedCountry = country
                                                viewModel.updateCountry(country)
                                                isExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        OutlinedTextField(
                            enabled = selectedCountry != null,
                            value = viewModel.phone,
                            placeholder = { Text(stringResource(Res.string.phone)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(RoundedCornerShape(25)),
                            shape = RoundedCornerShape(25),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            prefix = {
                                Row {
                                    if (selectedCountry == null) {
                                        Text("+", style = MaterialTheme.typography.bodyLarge)
                                    } else {
                                        Text(selectedCountry!!.code, style = MaterialTheme.typography.bodyLarge)
                                    }
                                    Spacer(Modifier.width(8.dp))
                                    Text("|", style = MaterialTheme.typography.bodyMedium)
                                    Spacer(Modifier.width(8.dp))
                                }
                            },
                            maxLines = 1,
                            onValueChange = { viewModel.updatePhone(it) },
                            isError = viewModel.phoneHasErrors,
                        )
                    } else {
                        OutlinedTextField(
                            value = viewModel.email,
                            onValueChange = { viewModel.updateEmail(it) },
                            placeholder = { Text(stringResource(Res.string.email)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                                .clip(RoundedCornerShape(25)),
                            shape = RoundedCornerShape(25),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            maxLines = 1,
                        )
                    }

                    OutlinedTextField(
                        enabled = viewModel.isPasswordEnabled(),
                        value = viewModel.password,
                        onValueChange = { viewModel.updatePassword(it) },
                        placeholder = { Text(stringResource(Res.string.password)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clip(RoundedCornerShape(25)),
                        shape = RoundedCornerShape(25),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        maxLines = 1,
                        suffix = {
                            if (isPasswordVisible) {
                                IconButton(
                                    onClick = { isPasswordVisible = !isPasswordVisible },
                                    modifier = Modifier.size(20.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Visibility,
                                        contentDescription = stringResource(Res.string.password)
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = { isPasswordVisible = !isPasswordVisible },
                                    modifier = Modifier.size(20.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.VisibilityOff,
                                        contentDescription = stringResource(Res.string.password)
                                    )
                                }
                            }
                        },
                        visualTransformation = if (isPasswordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                    )

                    Button(
                        onClick = { viewModel.login() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                            .height(50.dp)
                            .clip(RoundedCornerShape(25)),
                        shape = RoundedCornerShape(25),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.scrim)
                    ) {
                        Text(
                            text = stringResource(Res.string.log_in),
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                    Text(
                        text = stringResource(Res.string.forgot_password),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { /* Handle not a member */ },
                        modifier = Modifier
                            .weight(2f)
                            .padding(horizontal = 8.dp)
                            .height(50.dp)
                            .clip(RoundedCornerShape(25)),
                        shape = RoundedCornerShape(25),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                        )
                    ) {
                        Text(
                            text = stringResource(Res.string.not_a_member_yet),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.scrim
                        )
                    }
                    Button(
                        onClick = { /* Handle join now */ },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                            .height(50.dp)
                            .clip(RoundedCornerShape(25)),
                        shape = RoundedCornerShape(25),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.scrim
                        )
                    ) {
                        Text(
                            text = stringResource(Res.string.join_now),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
            if (viewModel.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}