package com.example.mobile_client_app

import androidx.compose.ui.window.ComposeUIViewController
import com.example.mobile_client_app.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin(
            config = {
                modules(iosKoinModules)
            }
        )
    }
) { App() }