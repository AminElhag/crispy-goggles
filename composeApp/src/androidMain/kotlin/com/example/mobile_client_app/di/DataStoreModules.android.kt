package com.example.mobile_client_app.di

import com.example.mobile_client_app.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val dataStoreModule: Module
    get() = module { single { createDataStore(androidContext()) } }