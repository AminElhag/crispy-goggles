package com.example.mobile_client_app.common.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.error
import mobile_client_app.composeapp.generated.resources.ok
import mobile_client_app.composeapp.generated.resources.retry
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CustomErrorDialog(
    showDialog: Boolean,
    title: StringResource = Res.string.error,
    message: String,
    onDismissMessage: StringResource = Res.string.ok,
    onDismiss: () -> Unit,
    onRetryMessage: StringResource = Res.string.retry,
    onRetry: (() -> Unit)? = null
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            icon = {
                Icon(
                    Icons.Default.Error,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error
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
                TextButton(onClick = onDismiss) {
                    Text(stringResource(onDismissMessage))
                }
            },
            dismissButton = onRetry?.let { retry ->
                {
                    TextButton(onClick = {
                        onDismiss()
                        retry()
                    }) {
                        Text(stringResource(onRetryMessage))
                    }
                }
            }
        )
    }
}