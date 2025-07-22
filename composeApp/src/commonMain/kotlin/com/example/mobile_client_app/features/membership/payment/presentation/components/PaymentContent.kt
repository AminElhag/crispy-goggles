package com.example.mobile_client_app.features.membership.payment.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.common.component.RoundedCornerButton
import com.example.mobile_client_app.features.membership.main.domain.model.CheckoutInitResponse
import com.example.mobile_client_app.features.membership.payment.presentation.PaymentViewModel
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.complete_signup
import org.jetbrains.compose.resources.stringResource

@Composable
fun PaymentContent(
    innerPadding: PaddingValues,
    data: CheckoutInitResponse,
    viewModel: PaymentViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            PaymentSummary(data)

            CardFormFields(viewModel = viewModel)
        }


        RoundedCornerButton(
            onClick = {
                viewModel.onCompleteClick(data.contractId)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(50)),
            text = stringResource(Res.string.complete_signup)
        )

    }
}