package com.example.mobile_client_app.features.membership.payment.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.example.mobile_client_app.common.component.RoundedCornerWithoutBackgroundTextField

@Composable
fun ExpirationDateField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    RoundedCornerWithoutBackgroundTextField(
        value = value,
        onValueChange = { text ->
            onValueChange(text)
        },
        placeholder = "MM/YY",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}