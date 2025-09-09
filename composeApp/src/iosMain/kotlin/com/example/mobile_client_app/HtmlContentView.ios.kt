package com.example.mobile_client_app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun HtmlContentView(htmlContent: String, modifier: Modifier) {
    UIKitView(
        factory = {
            WKWebView().apply {
                loadHTMLString(htmlContent, null)
            }
        },
        modifier = modifier
    )
}