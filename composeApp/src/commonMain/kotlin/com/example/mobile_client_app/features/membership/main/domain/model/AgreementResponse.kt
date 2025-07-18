package com.example.mobile_client_app.features.membership.main.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AgreementResponse(
    val id: Long = 0,

    val title: String,

    val body: String,

    val required: Boolean,
) {
    fun toDto() = AgreementDto(
        id = id,
        title = title,
        body = body,
        required = required,
        selected = false
    )
}