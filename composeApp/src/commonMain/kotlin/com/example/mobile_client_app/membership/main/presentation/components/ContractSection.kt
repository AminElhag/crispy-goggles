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
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.membership
import org.jetbrains.compose.resources.stringResource


@Composable
fun ContractSection(viewModel: MembershipViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(Res.string.membership),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        viewModel.contractOptions.forEach {option ->
            ContractOption(
                text = option.title,
                subText = option.description,
                isSelected = viewModel.isContractOptionSelected(option),
                onSelect = { viewModel.selectContractOption(option) }
            )
        }

    }
}