package com.example.mobile_client_app.membership.main.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.mobile_client_app.common.component.FullScreenError
import com.example.mobile_client_app.common.component.FullScreenLoading
import com.example.mobile_client_app.membership.main.presentation.components.MembershipContent
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.membership
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun MembershipScreen(
    viewModel: MembershipViewModel = koinViewModel(),
    onNavigateToBackPage: () -> Unit,
    onContinue: () -> Unit,
    ) {

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = viewModel.today,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis in viewModel.today..viewModel.maxDate
            }
        }
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is UiState.Loading -> FullScreenLoading()
            is UiState.Error -> FullScreenError(
                errorMessage = state.message,
                onRetry = { viewModel.fetchMembershipPlans() }
            )
            is UiState.Success -> Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text(stringResource(Res.string.membership)) },
                        /*navigationIcon = {
                            IconButton(onClick = onNavigateToBackPage) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        }*/
                    )
                },
                content = { innerPadding ->
                    MembershipContent(
                        modifier = Modifier
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState()),
                        viewModel = viewModel,
                        onContinue = {viewModel.onCheckInfo()},
                        datePickerState = datePickerState
                    )
                }
            )

            is UiState.ShowSnackbar -> TODO()
        }
    }
}