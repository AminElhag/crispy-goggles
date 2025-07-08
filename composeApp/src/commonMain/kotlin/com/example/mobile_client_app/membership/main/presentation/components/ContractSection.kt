package com.example.mobile_client_app.membership.main.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mobile_client_app.membership.main.presentation.ContractType
import com.example.mobile_client_app.membership.main.presentation.MembershipViewModel


@Composable
fun ContractSection(viewModel: MembershipViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Contract",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

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