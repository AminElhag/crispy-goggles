package com.example.mobile_client_app.features.auth.profile.domain.models

import com.example.mobile_client_app.features.auth.profile.data.remote.models.FreezingRequest
import io.ktor.http.cio.Request
import kotlinx.datetime.LocalDate

data class DateRange(
    val startDate: LocalDate?,
    val endDate: LocalDate?
) {
    fun toRequest() = FreezingRequest(
        startDate,
        endDate
    )
}