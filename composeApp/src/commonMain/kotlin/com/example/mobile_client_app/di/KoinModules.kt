package com.example.mobile_client_app.di

import com.example.mobile_client_app.features.auth.login.di.loginModule
import com.example.mobile_client_app.features.auth.profile.profileModule
import com.example.mobile_client_app.features.auth.registering.di.registeringModule
import com.example.mobile_client_app.features.classes.bookingClass.bookingClassModel
import com.example.mobile_client_app.features.membership.main.di.membershipModule
import com.example.mobile_client_app.features.membership.payment.di.paymentModule
import com.example.mobile_client_app.features.notifications.di.notificationsModule
import com.example.mobile_client_app.features.onboarding.di.onBoardingModule
import com.example.mobile_client_app.features.personalTraining.appointments.appointmentsModule
import com.example.mobile_client_app.features.personalTraining.trainerSelection.trainerSelection
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            networkModule,
            loginModule,
            registeringModule,
            dataStoreModule,
            membershipModule,
            paymentModule,
            onBoardingModule,
            notificationsModule,
            profileModule,
            appointmentsModule,
            trainerSelection,
            bookingClassModel,
        )
    }