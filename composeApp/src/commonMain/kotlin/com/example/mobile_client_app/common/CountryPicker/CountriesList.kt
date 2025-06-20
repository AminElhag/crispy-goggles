package com.example.mobile_client_app.common.CountryPicker

import mobile_client_app.composeapp.generated.resources.Res
import mobile_client_app.composeapp.generated.resources.saudi
import mobile_client_app.composeapp.generated.resources.saudi_arabia
import mobile_client_app.composeapp.generated.resources.sudan
import org.jetbrains.compose.resources.getString

val country = mutableListOf(
    Country(Res.string.sudan, "+249", Res.drawable.sudan),
    Country(Res.string.saudi, "+966", Res.drawable.saudi_arabia),
)