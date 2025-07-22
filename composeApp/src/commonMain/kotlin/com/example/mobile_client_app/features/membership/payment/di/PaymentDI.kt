package com.example.mobile_client_app.features.membership.payment.di

import com.example.mobile_client_app.features.membership.payment.data.remote.PaymentAPI
import com.example.mobile_client_app.features.membership.payment.data.remote.PaymentAPIImpl
import com.example.mobile_client_app.features.membership.payment.domain.repository.PaymentRepository
import com.example.mobile_client_app.features.membership.payment.domain.repository.impl.PaymentRepositoryImpl
import com.example.mobile_client_app.features.membership.payment.domain.usecase.RequestPaymentUseCase
import com.example.mobile_client_app.features.membership.payment.presentation.PaymentViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val paymentModule = module {
    viewModel { PaymentViewModel(get()) }
    single { RequestPaymentUseCase(get()) }
    single<PaymentRepository> { PaymentRepositoryImpl(get()) }
    single<PaymentAPI> { PaymentAPIImpl(get()) }
}