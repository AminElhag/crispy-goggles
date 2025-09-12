package com.example.mobile_client_app.features.classes.bookingClass.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_client_app.common.component.CustomErrorDialog
import com.example.mobile_client_app.common.component.CustomMessageDialog
import com.example.mobile_client_app.common.component.FullScreenError
import com.example.mobile_client_app.common.component.FullScreenLoading
import com.example.mobile_client_app.common.component.RoundedCornerButton
import com.example.mobile_client_app.common.component.SimpleLoadingOverlay
import com.example.mobile_client_app.features.classes.bookingClass.presentation.components.DataChip
import mobile_client_app.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingClassScreen(
    viewModel: BookingClassViewModel = koinViewModel(),
    classId: Long,
    onCancelBookingClick: () -> Unit,
    onConfirmClick:() -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setClassId(classId)
    }

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
                    title = { Text(stringResource(Res.string.schedule)) },
                    navigationIcon = {
                        IconButton(onClick = onCancelBookingClick) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null)
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
            ) {
                Image(
                    painter = painterResource(Res.drawable.login_main_image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(
                        text = stringResource(Res.string.duration),
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    )
                    DataChip(
                        text = uiState.date!!.duration,
                        modifier = Modifier.padding(vertical = 16.dp),
                    )
                    Text(
                        text = stringResource(Res.string.intensity),
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        maxItemsInEachRow = 3,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        uiState.date!!.intensity.forEach{
                            DataChip(
                                text = it,
                                modifier = Modifier.padding(vertical = 16.dp),
                            )
                        }
                    }
                    uiState.date!!.equipment?.let {
                        Text(
                            text = stringResource(Res.string.equipment),
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            maxItemsInEachRow = 3,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            uiState.date!!.equipment!!.forEach {
                                DataChip(
                                    text = it,
                                    modifier = Modifier.padding(vertical = 16.dp),
                                )
                            }
                        }
                    }
                    Text(
                        text = stringResource(Res.string.trainer),
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF4CAF50)), // Greenish color for avatar
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "SM",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = uiState.date!!.trainer.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )

                            Text(
                                text = uiState.date!!.trainer.specialty,
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 2.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = uiState.date!!.trainer.discretion,
                                fontSize = 14.sp,
                                color = Color.Black.copy(alpha = 0.7f),
                                lineHeight = 20.sp
                            )
                        }
                    }
                    RoundedCornerButton(
                        onClick = {
                            viewModel.sendBookingRequest()
                        },
                        text = stringResource(Res.string.book_now),
                        modifier = Modifier.fillMaxWidth().height(50.dp).clip(RoundedCornerShape(25)),
                    )

                    if (uiState.isBookingClassRequest){
                        SimpleLoadingOverlay()
                    }

                    CustomMessageDialog(
                        showDialog = uiState.isBookingClassRequestComparable,
                        title = Res.string.successful,
                        message = stringResource(Res.string.class_successful_message),
                        onConfirmClick = {
                            onConfirmClick()
                        },
                    )

                    CustomErrorDialog(
                        showDialog = uiState.bookingClassRequestError != null,
                        message = uiState.bookingClassRequestError?: stringResource(Res.string.generic_error),
                        onDismiss = {
                            viewModel.sendBookingRequest()
                        },
                        onRetryMessage = Res.string.retry
                    )
                }
            }
        }
    }
}