package com.example.mobile_client_app.features.membership.main.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.mobile_client_app.common.component.FullScreenError
import com.example.mobile_client_app.common.component.FullScreenLoading
import com.example.mobile_client_app.features.membership.main.domain.model.CheckoutInitResponse
import com.example.mobile_client_app.features.membership.main.presentation.components.MembershipContent
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun MembershipScreen(
    viewModel: MembershipViewModel = koinViewModel(),
    onNavigateToBackPage: () -> Unit,
    onContinue: (data: CheckoutInitResponse) -> Unit,
) {

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = viewModel.today,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis in viewModel.minDate..viewModel.maxDate
            }
        }
    )
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is UiState.Loading -> FullScreenLoading()
            is UiState.Error -> FullScreenError(
                errorMessage = state.message,
                onRetry = { viewModel.fetchMembershipPlans() }
            )

            is UiState.Success -> MembershipContent(
                viewModel = viewModel,
                onContinue = { viewModel.onCheckInfo() },
                datePickerState = datePickerState,
                onSuccess = { onContinue(it) },
            )
        }
    }
}