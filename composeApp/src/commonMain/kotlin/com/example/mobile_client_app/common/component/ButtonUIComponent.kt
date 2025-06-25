package com.example.mobile_client_app.common.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RoundedCornerButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    percentOfRoundedCornerShape: Int = 25,
    text: String,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(percentOfRoundedCornerShape),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.scrim)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}