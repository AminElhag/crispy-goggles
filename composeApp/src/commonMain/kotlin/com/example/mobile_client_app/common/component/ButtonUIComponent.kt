package com.example.mobile_client_app.common.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun RoundedCornerButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    percentOfRoundedCornerShape: Int = 25,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.scrim),
    border: BorderStroke = BorderStroke(0.dp, Color.Transparent),
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(percentOfRoundedCornerShape),
        colors = colors,
        enabled = enabled,
        border = border
    ) {
        Text(
            text = text,
            style = textStyle,
        )
    }
}