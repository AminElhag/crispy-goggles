package com.example.mobile_client_app.features.personalTraining.trainerSelection.presntation


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.common.component.FullScreenError
import com.example.mobile_client_app.common.component.FullScreenLoading
import com.example.mobile_client_app.common.component.RoundedCornerButton
import com.example.mobile_client_app.features.personalTraining.trainerSelection.presntation.components.TrainerItem
import mobile_client_app.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainerSelectionScreen(
    viewModel: TrainerSelectionViewModel = koinViewModel(),
    onCancelBookingClick: () -> Unit,
) {
    var selectedTrainer by remember { mutableStateOf<String?>(null) }
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
                    title = { Text(stringResource(Res.string.book_a_session)) },
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
                    .padding(innerPadding).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(
                            Res.string.step_number_of, 1, 3
                        ),
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(
                            MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f),
                            RoundedCornerShape(4.dp)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.33f)
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(4.dp))
                    )
                }
                Text(
                    text = stringResource(Res.string.choose_a_trainer),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.trainerResponses) { trainer ->
                        TrainerItem(
                            trainerResponse = trainer,
                            isSelected = selectedTrainer == trainer.id,
                            onSelect = { selectedTrainer = trainer.id }
                        )
                    }
                }
                RoundedCornerButton(
                    onClick = {},
                    text = stringResource(Res.string.next),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(25)),
                )
            }
        }
    }
}
