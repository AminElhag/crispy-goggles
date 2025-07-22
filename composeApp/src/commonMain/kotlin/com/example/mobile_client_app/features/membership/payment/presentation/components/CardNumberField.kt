package com.example.mobile_client_app.features.membership.payment.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.example.mobile_client_app.common.component.RoundedCornerWithoutBackgroundTextField

@Composable
fun CardNumberField(
    value: TextFieldValue, // Changed from String to TextFieldValue
    onValueChange: (TextFieldValue) -> Unit,
) {
    RoundedCornerWithoutBackgroundTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = "Card Number",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}