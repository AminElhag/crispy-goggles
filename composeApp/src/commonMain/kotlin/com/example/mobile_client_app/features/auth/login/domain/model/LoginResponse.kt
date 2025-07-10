package com.example.mobile_client_app.features.auth.login.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
)
