package com.example.mobile_client_app.membership.main.di

import com.example.mobile_client_app.membership.main.data.remote.MembershipAPI
import com.example.mobile_client_app.membership.main.data.remote.MembershipAPIImpl
import com.example.mobile_client_app.membership.main.domain.repository.MembershipRepository
import com.example.mobile_client_app.membership.main.domain.repository.MembershipRepositoryImpl
import com.example.mobile_client_app.membership.main.domain.usercase.GetMembershipUserCase
import com.example.mobile_client_app.membership.main.presentation.MembershipViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val membershipModule = module {
    viewModel { MembershipViewModel(get(),get()) }
    single { GetMembershipUserCase(get()) }
    single<MembershipRepository> { MembershipRepositoryImpl(get()) }
    single<MembershipAPI> { MembershipAPIImpl(get()) }
}