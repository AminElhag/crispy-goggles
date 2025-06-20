package com.example.mobile_client_app

import com.example.mobile_client_app.common.createHttpClientEngine
import org.koin.dsl.module

val iosKoinModules = module {
    single { createHttpClientEngine() }
}