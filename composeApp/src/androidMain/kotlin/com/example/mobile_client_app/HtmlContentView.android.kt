package com.example.mobile_client_app

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun HtmlContentView(htmlContent: String, modifier: Modifier) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                loadDataWithBaseURL(null, htmlContent.trimIndent(), "text/html", "utf-8", null)
            }
        },
        modifier = modifier
    )
}