package com.example.mobile_client_app.features.membership.main.domain.model

data class AgreementDto(
    val id: Long = 0,

    val title: String,

    val body: String,

    val required: Boolean,

    val selected: Boolean = false,
)