package com.example.mobile_client_app.membership.main.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.common.component.DateSection
import com.example.mobile_client_app.common.component.RoundedCornerButton
import com.example.mobile_client_app.common.component.RoundedCornerWithoutBackgroundTextField
import mobile_client_app.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MembershipScreen(
    onNavigateToBackPage: () -> Unit,
    onContinue: () -> Unit,
    viewModel: MembershipViewModel = remember { MembershipViewModel() }
) {
    val datePickerState = rememberDatePickerState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Membership") },
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
                onContinue = onContinue,
                datePickerState = datePickerState
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MembershipContent(
    modifier: Modifier = Modifier,
    viewModel: MembershipViewModel,
    datePickerState: DatePickerState,
    onContinue: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Plans section
        Text(
            text = stringResource(Res.string.choose_your_plan),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        // Responsive plan cards
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
                Plan.entries.forEach { plan ->
                    PlanCard(
                        plan = plan,
                        isSelected = plan == viewModel.selectedPlan,
                        onSelect = { viewModel.selectedPlan = plan },
                        modifier = if (isWideScreen) Modifier.weight(1f) else Modifier.fillMaxWidth()
                    )
                }
            }
        } else {
            Column(verticalArrangement = arrangement, modifier = Modifier.fillMaxWidth()) {
                Plan.entries.forEach { plan ->
                    PlanCard(
                        plan = plan,
                        isSelected = plan == viewModel.selectedPlan,
                        onSelect = { viewModel.selectedPlan = plan },
                        modifier = if (isWideScreen) Modifier.weight(1f) else Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Contract Type
        ContractSection(viewModel)

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

        // Continue Button
        Spacer(modifier = Modifier.weight(1f))
        RoundedCornerButton(
            onClick = {

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

@Composable
fun ContractSection(viewModel: MembershipViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Contract",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Column() {
            ContractOption(
                text = "12-month contract",
                subText = "12 months",
                isSelected = viewModel.contractType == ContractType.YEARLY,
                onSelect = { viewModel.contractType = ContractType.YEARLY }
            )

            ContractOption(
                text = "Month-to-month",
                subText = "Month-to-month",
                isSelected = viewModel.contractType == ContractType.MONTHLY,
                onSelect = { viewModel.contractType = ContractType.MONTHLY }
            )
        }
    }
}


@Composable
fun PromoCodeSection(viewModel: MembershipViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Promo Code",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun PlanCard(
    plan: Plan,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = /*if (isSelected) Color(0xFFE8F5E9) else*/ MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
        ),
        border = /*if (isSelected) CardDefaults.outlinedCardBorder() else null*/ CardDefaults.outlinedCardBorder(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Plan title
            Text(
                text = plan.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = /*if (isSelected) Color(0xFF2E7D32) else*/ MaterialTheme.colorScheme.onSurface
            )

            // Price
            Text(
                text = plan.price,
                style = MaterialTheme.typography.titleMedium,
            )

            // Choose button
            Button(
                onClick = onSelect,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = /*if (isSelected) Color(0xFF4CAF50) else */MaterialTheme.colorScheme.onPrimary,
                    contentColor = Color.White
                )
            ) {
                Text("Choose Plan")
            }

            // Features
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                plan.features.forEach { feature ->
                    Text(
                        text = feature,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun ContractOption(
    text: String,
    subText: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onSelect)
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelect,
        )

        Column {
            Text(text, style = MaterialTheme.typography.bodyMedium)
            Text(subText, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }
}