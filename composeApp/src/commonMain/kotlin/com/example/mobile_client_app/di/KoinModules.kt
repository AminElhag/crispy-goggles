package com.example.mobile_client_app.di

import com.example.mobile_client_app.features.auth.login.di.loginModule
import com.example.mobile_client_app.features.auth.registering.di.registeringModule
import com.example.mobile_client_app.features.membership.main.di.membershipModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            networkModule,loginModule,registeringModule,dataStoreModule,membershipModule
        )
    }