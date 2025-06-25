package com.example.mobile_client_app.auth.registering.di

import com.example.mobile_client_app.auth.registering.presentaion.ui.RegisteringViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val registeringModule = module {
    viewModel { RegisteringViewModel() }
}