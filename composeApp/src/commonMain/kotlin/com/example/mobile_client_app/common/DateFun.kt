package com.example.mobile_client_app.common

import com.example.mobile_client_app.common.models.DateRangePickerConfig
import kotlinx.datetime.LocalDate

fun formatDateRange(startDate: LocalDate?, endDate: LocalDate?, config: DateRangePickerConfig): String {
    return when {
        startDate == null -> if (config.allowStartDateSelection) "No dates selected" else "Start date disabled"
        endDate == null -> if (config.allowEndDateSelection) "Start: ${formatDate(startDate)}" else "End: ${
            formatDate(
                startDate
            )
        }"

        else -> "${formatDate(startDate)} - ${formatDate(endDate)}"
    }
}

fun formatDate(date: LocalDate): String {
    return "${date.day} ${date.month.name.take(3)} ${date.year}"
}