package com.example.mobile_client_app.auth.login.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.login_main_image
import mobile_client_app.composeapp.generated.resources.saudi_arabia
import mobile_client_app.composeapp.generated.resources.sudan
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

data class Country(
    val name: String,
    val code: String,
    val flag: DrawableResource,
)

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier.fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
) {
    var isPhoneSelected by remember { mutableStateOf(true) }
    var isExpanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf<Country?>(null) }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val country = mutableListOf<Country>(
        Country("SUDAN", "+249", Res.drawable.sudan),
        Country("SAUDI", "+966", Res.drawable.saudi_arabia),
    )

    Scaffold {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
            Image(
                painter = painterResource(Res.drawable.login_main_image), // Replace with your actual image resource
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Welcome back",
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
                        onClick = { isPhoneSelected = true },
                        shape = RoundedCornerShape(30),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isPhoneSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.surfaceContainerHighest
                        ),
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = "Phone",
                            color = if (isPhoneSelected) MaterialTheme.colorScheme.scrim else MaterialTheme.colorScheme.tertiary
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = { isPhoneSelected = false },
                        shape = RoundedCornerShape(30),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!isPhoneSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.surfaceContainerHighest
                        ),
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = "Email",
                            color = if (!isPhoneSelected) MaterialTheme.colorScheme.scrim else MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }
                if (isPhoneSelected) {
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
                                        Text(selectedCountry!!.name, style = MaterialTheme.typography.bodySmall)
                                    }
                                }else{
                                    Text(
                                        text = "Selected Country",
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
                                            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                                                Row {
                                                    Image(
                                                        painter = painterResource(country.flag),
                                                        contentDescription = null,
                                                        modifier = Modifier.size(20.dp)
                                                    )
                                                    Spacer(Modifier.width(8.dp))
                                                    Text(country.name, style = MaterialTheme.typography.bodySmall)
                                                }
                                                Text(country.code, style = MaterialTheme.typography.bodyMedium)
                                            }
                                        },
                                        onClick = {
                                            selectedCountry = country
                                            isExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        placeholder = { Text("Phone number") },
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
                    )
                }else{
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clip(RoundedCornerShape(25)),
                        shape = RoundedCornerShape(25),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        maxLines = 1,
                    )
                }

            Button(
                onClick = { /* Handle login */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(25)),
                shape = RoundedCornerShape(25),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.scrim)
            ) {
                Text(
                    text = "Log in",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Text(
                text = "Forgot password?",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            ) }
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
                        text = "Not a member yet?",
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
                        text = "Join now",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}