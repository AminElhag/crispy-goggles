package com.example.mobile_client_app.membership.main.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

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