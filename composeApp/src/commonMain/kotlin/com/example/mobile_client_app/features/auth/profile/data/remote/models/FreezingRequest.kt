package com.example.mobile_client_app.features.auth.profile.data.remote.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FreezingRequest(
    @SerialName("start_date")
    val startDate: LocalDate?,
    @SerialName("end_date")
    val endDate: LocalDate?
)
