package com.example.mobile_client_app.common.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.cancel
import mobile_client_app.composeapp.generated.resources.ok
import mobile_client_app.composeapp.generated.resources.warning
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

//confirm
@Composable
fun CustomMessageDialog(
    showDialog: Boolean,
    title: StringResource = Res.string.warning,
    message: String,
    onConfirmMessage: StringResource = Res.string.ok,
    onConfirmClick: () -> Unit,
    onCancelMessage: StringResource = Res.string.cancel,
    onCancelClick: () -> Unit,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onConfirmClick,
            icon = {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = "Warning",
                    tint = MaterialTheme.colorScheme.outline
                )
            },
            title = {
                Text(
                    stringResource(title),
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Text(
                    message,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(onClick = onConfirmClick) {
                    Text(stringResource(onConfirmMessage))
                }
            },
            dismissButton =
                {
                    TextButton(onClick = {
                        onCancelClick()
                    }) {
                        Text(stringResource(onCancelMessage))
                    }
                }
        )
    }
}