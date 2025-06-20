package com.example.mobile_client_app.common.CountryPicker

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class Country(
    val nameRes: StringResource,
    val code: String,
    val flag: DrawableResource,
)