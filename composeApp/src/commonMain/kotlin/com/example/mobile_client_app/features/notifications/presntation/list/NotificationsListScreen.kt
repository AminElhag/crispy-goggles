package com.example.mobile_client_app.features.notifications.presntation.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.mobile_client_app.common.component.FullScreenError
import com.example.mobile_client_app.common.component.FullScreenLoading
import com.example.mobile_client_app.features.notifications.presntation.list.components.NotificationCard
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.notifications
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsListScreen(
    viewModel: NotificationsListViewModel = koinViewModel(),
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
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
                    title = { Text(text = stringResource(Res.string.notifications)) },
                    navigationIcon = {
                        IconButton(onClick = { onBack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
            ) {
                items(uiState.list) { notification ->
                    NotificationCard(notification = notification)
                }
            }
        }
    }
}