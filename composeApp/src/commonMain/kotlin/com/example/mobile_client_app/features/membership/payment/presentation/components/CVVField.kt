package com.example.mobile_client_app.features.membership.payment.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.example.mobile_client_app.common.component.RoundedCornerWithoutBackgroundTextField

@Composable
fun CVVField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    RoundedCornerWithoutBackgroundTextField(
        value = value,
        onValueChange = { text ->
            // Limit to 3-4 digits
                onValueChange(text)

        },
        placeholder = "CVV",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}