package com.example.mobile_client_app.features.auth.registering.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserResponse(
    var token: String? = null,
)
