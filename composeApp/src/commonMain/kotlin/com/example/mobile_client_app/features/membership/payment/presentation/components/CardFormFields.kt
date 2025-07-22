package com.example.mobile_client_app.features.membership.payment.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.features.membership.payment.presentation.PaymentViewModel

@Composable
fun CardFormFields(viewModel: PaymentViewModel) {

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CardNumberField(
            value = viewModel.cardNumber,
            onValueChange = { viewModel.updateCardNumber(it) },
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            ExpirationDateField(
                value = viewModel.expirationDate,
                onValueChange = { viewModel.updateExpirationDate(it) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

            CVVField(
                value = viewModel.cardCVV,
                onValueChange = { viewModel.updateCardCVV(it) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }
    }
}