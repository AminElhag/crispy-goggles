package com.example.mobile_client_app.membership.main.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.common.component.DateSection
import com.example.mobile_client_app.common.component.RoundedCornerButton
import com.example.mobile_client_app.common.component.RoundedCornerWithoutBackgroundTextField
import com.example.mobile_client_app.membership.main.presentation.MembershipViewModel
import com.example.mobile_client_app.membership.main.presentation.Plan
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.cancel
import mobile_client_app.composeapp.generated.resources.continue_string
import mobile_client_app.composeapp.generated.resources.ok
import mobile_client_app.composeapp.generated.resources.promo_code
import mobile_client_app.composeapp.generated.resources.start_Date
import org.jetbrains.compose.resources.stringResource


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
    ) { // Responsive plan cards
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