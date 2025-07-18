package com.example.mobile_client_app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun HtmlContentView(htmlContent: String, modifier: Modifier)