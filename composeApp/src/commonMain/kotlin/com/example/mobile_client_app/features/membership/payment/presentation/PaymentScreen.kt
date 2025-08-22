package com.example.mobile_client_app.features.membership.payment.presentation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.example.mobile_client_app.common.component.CustomErrorDialog
import com.example.mobile_client_app.common.component.LoadingOverlay
import com.example.mobile_client_app.features.membership.main.domain.model.CheckoutInitResponse
import com.example.mobile_client_app.features.membership.payment.presentation.components.PaymentContent
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.generic_error
import mobile_client_app.composeapp.generated.resources.payment
import mobile_client_app.composeapp.generated.resources.retry
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    data: CheckoutInitResponse,
    viewModel: PaymentViewModel = koinViewModel(),
    onCompleted: () -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val event = viewModel.events.collectAsState().value


    LaunchedEffect(event) {
        when (event) {
            is PaymentEvent.CheckoutInit -> {

            }

            PaymentEvent.Reset -> {

            }

            is PaymentEvent.ShowSnackbar -> {
                snackbarHostState.showSnackbar(event.message)
                viewModel.resetEvent()
            }

            is PaymentEvent.PaymentComplete -> {
                if (event.boolean) {
                    onCompleted()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(Res.string.payment)) },
            )
        },
        content = { innerPadding ->
            PaymentContent(
                innerPadding = innerPadding,
                data = data,
                viewModel = viewModel,
            )
        }
    )

    if (viewModel.isLoading) {
        LoadingOverlay()
    }
    CustomErrorDialog(
        showDialog = true,
        message = viewModel.errorDialogMessage ?: stringResource(Res.string.generic_error),
        onDismiss = {
            viewModel.updateShowErrorDialog(false)
            onCompleted()
        },
        onRetry = {
            viewModel.updateShowErrorDialog(false)
        },
        onRetryMessage = Res.string.retry
    )
}