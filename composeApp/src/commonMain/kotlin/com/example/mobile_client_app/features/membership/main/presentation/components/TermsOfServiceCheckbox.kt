package com.example.mobile_client_app.features.membership.main.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.i_agree_to_the
import mobile_client_app.composeapp.generated.resources.required
import org.jetbrains.compose.resources.stringResource

@Composable
fun TermsOfServiceCheckbox(
    modifier: Modifier = Modifier,
    checkedState: Boolean,
    title: String,
    required: Boolean,
    openTermsOfService: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = onCheckedChange,
        )
        Text(stringResource(Res.string.i_agree_to_the), style = MaterialTheme.typography.bodySmall)
        Text(
            text = title,
            modifier = Modifier
                .clickable { openTermsOfService() }
                .padding(horizontal = 4.dp),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodySmall.copy(
                textDecoration = TextDecoration.Underline
            )
        )
        Text(
            if (required) stringResource(Res.string.required) else stringResource(Res.string.required),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
        )
    }
}