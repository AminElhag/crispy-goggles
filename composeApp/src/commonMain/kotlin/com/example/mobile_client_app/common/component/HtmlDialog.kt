package com.example.mobile_client_app.common.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mobile_client_app.HtmlContentView
import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.dismiss
import org.jetbrains.compose.resources.stringResource
import saschpe.log4k.Log

@Composable
fun HtmlDialog(
    title: String,
    htmlContent: String,
    onDismiss: () -> Unit
) {
    Log.info("HtmlDialog content: $htmlContent")
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth().heightIn(min = 300.dp),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                HtmlContentView(
                    htmlContent = htmlContent,
                    modifier = Modifier.weight(1f).fillMaxWidth()
                        .heightIn(min = 100.dp)
                )
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End).padding(top = 16.dp)
                ) {
                    Text(stringResource(Res.string.dismiss))
                }
            }
        }
    }
}