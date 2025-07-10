package com.example.mobile_client_app.features.membership.main.di


import com.example.mobile_client_app.features.membership.main.data.remote.MembershipAPI
import com.example.mobile_client_app.features.membership.main.data.remote.MembershipAPIImpl
import com.example.mobile_client_app.features.membership.main.domain.repository.MembershipRepository
import com.example.mobile_client_app.features.membership.main.domain.repository.MembershipRepositoryImpl
import com.example.mobile_client_app.features.membership.main.domain.useCase.CheckPromoCodeUseCase
import com.example.mobile_client_app.features.membership.main.domain.useCase.CheckoutInitUseCase
import com.example.mobile_client_app.features.membership.main.domain.useCase.GetMembershipUseCase
import com.example.mobile_client_app.features.membership.main.presentation.MembershipViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val membershipModule = module {
    viewModel { MembershipViewModel(get(), get(), get()) }
    single { GetMembershipUseCase(get()) }
    single { CheckPromoCodeUseCase(get()) }
    single { CheckoutInitUseCase(get()) }
    single<MembershipRepository> { MembershipRepositoryImpl(get()) }
    single<MembershipAPI> { MembershipAPIImpl(get()) }
}