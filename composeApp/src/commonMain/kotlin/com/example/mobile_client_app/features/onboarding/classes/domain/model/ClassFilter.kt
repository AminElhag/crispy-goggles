package com.example.mobile_client_app.features.onboarding.classes.domain.model

import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.all
import mobile_client_app.composeapp.generated.resources.today
import mobile_client_app.composeapp.generated.resources.tomorrow
import org.jetbrains.compose.resources.StringResource

enum class ClassFilter(val displayName: StringResource) {
    ALL(Res.string.all),
    TODAY(Res.string.today),
    TOMORROW(Res.string.tomorrow)
}