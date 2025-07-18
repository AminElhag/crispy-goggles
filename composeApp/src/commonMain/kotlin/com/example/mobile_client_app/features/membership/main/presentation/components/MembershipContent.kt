package com.example.mobile_client_app.features.membership.main.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.common.component.DateSection
import com.example.mobile_client_app.common.component.HtmlDialog
import com.example.mobile_client_app.common.component.RoundedCornerButton
import com.example.mobile_client_app.common.component.RoundedCornerWithoutBackgroundTextField
import com.example.mobile_client_app.features.membership.main.domain.model.CheckoutInitResponse
import com.example.mobile_client_app.features.membership.main.presentation.MembershipEvent
import com.example.mobile_client_app.features.membership.main.presentation.MembershipViewModel
import mobile_client_app.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import saschpe.log4k.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MembershipContent(
    viewModel: MembershipViewModel,
    datePickerState: DatePickerState,
    onContinue: () -> Unit,
    onSuccess: (data: CheckoutInitResponse) -> Unit,
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val event = viewModel.events.collectAsState().value
    LaunchedEffect(event) {
        when (event) {
            MembershipEvent.Reset -> {
                Log.debug { "Reset membership event" }
            }

            is MembershipEvent.ShowSnackbar -> {
                snackbarHostState.showSnackbar(
                    message = event.message,
                    duration = SnackbarDuration.Short
                )
                viewModel.resetEvent()
            }

            is MembershipEvent.CheckoutInit -> {
                onSuccess(event.data)
            }
        }
    }
    Scaffold(
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                val isWideScreen = /*LocalConfiguration.current.screenWidthDp > 600*/ false
                val arrangement = if (isWideScreen)
                    Arrangement.spacedBy(16.dp) else Arrangement.spacedBy(16.dp)

                /*val direction = if (isWideScreen)
                    Row(horizontalArrangement = arrangement, modifier = Modifier.fillMaxWidth())
                else
                    Column(verticalArrangement = arrangement, modifier = Modifier.fillMaxWidth())

                direction {

                }*/
                if (isWideScreen) {
                    Row(horizontalArrangement = arrangement, modifier = Modifier.fillMaxWidth()) {
                        viewModel.plans!!.plans.forEach { plan ->
                            PlanCard(
                                plan = plan,
                                isSelected = plan == viewModel.selectedPlan,
                                onSelect = { viewModel.updateSelectedPlan(plan) },
                                modifier = if (isWideScreen) Modifier.weight(1f) else Modifier.fillMaxWidth()
                            )
                        }
                    }
                } else {
                    Column(verticalArrangement = arrangement, modifier = Modifier.fillMaxWidth()) {
                        viewModel.plans!!.plans.forEach { plan ->
                            PlanCard(
                                plan = plan,
                                isSelected = plan == viewModel.selectedPlan,
                                onSelect = { viewModel.updateSelectedPlan(plan) },
                                modifier = if (isWideScreen) Modifier.weight(1f) else Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                /*if (!viewModel.contractOptions.isEmpty()) {
                    ContractSection(viewModel)
                }*/

                // Start Date
                DateSection(
                    datePickerState = datePickerState,
                    hasOutTitle = true,
                    title = stringResource(Res.string.start_Date),
                    onClick = { viewModel.updateShowDatePicker(!viewModel.showDatePicker) },
                    selectDate = viewModel.getSelectDateAsString(),
                    hintString = stringResource(Res.string.start_Date),
                    showDatePicker = viewModel.showDatePicker,
                    updateShowDatePicker = {
                        viewModel.updateShowDatePicker(it)
                    },
                    updateDateOfBirth = {
                        viewModel.updateDateOfBirth(it)
                    },
                    okString = stringResource(Res.string.ok),
                    cancelString = stringResource(Res.string.cancel)
                )

                // Promo Code
                RoundedCornerWithoutBackgroundTextField(
                    value = viewModel.promoCode,
                    onValueChange = { viewModel.updatePromoCode(it) },
                    placeholder = stringResource(Res.string.promo_code),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    hasOutTitle = true,
                    title = stringResource(Res.string.promo_code),
                )

                if (viewModel.hasAgreement()) {
                    viewModel.agreements.forEach { agreement ->
                        TermsOfServiceCheckbox(
                            checkedState = agreement.selected,
                            title = agreement.title,
                            openTermsOfService = {
                                viewModel.showTermsDialog(agreement, true)
                            },
                            onCheckedChange = { viewModel.updateAgreementSelect(agreement.id) },
                            required = agreement.required,
                        )
                    }
                }

                // Continue Button
                Spacer(modifier = Modifier.weight(1f))
                RoundedCornerButton(
                    onClick = {
                        onContinue()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(50)),
                    text = stringResource(Res.string.continue_string)
                )
            }
        }
    )
    if (viewModel.showAgreementDialog) {
        HtmlDialog(
            title = viewModel.dialogAgreement?.title ?: "",
            htmlContent = viewModel.dialogAgreement?.body ?: "",
            onDismiss = { viewModel.showTermsDialog(null, false) },
        )
    }
}