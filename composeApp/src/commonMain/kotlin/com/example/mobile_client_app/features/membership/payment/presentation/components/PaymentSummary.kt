package com.example.mobile_client_app.features.membership.payment.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.features.membership.main.domain.model.CheckoutInitResponse

@Composable
fun PaymentSummary(data: CheckoutInitResponse) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SummaryRow("Total amount subject to tax", data.totalAmountWithoutTax)
        SummaryRow("Value added tax", data.totalTax)
        SummaryRow("Total with tax", data.totalAmount, isBold = true)
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    isBold: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(label)
        Text(
            text = value,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
    }
}