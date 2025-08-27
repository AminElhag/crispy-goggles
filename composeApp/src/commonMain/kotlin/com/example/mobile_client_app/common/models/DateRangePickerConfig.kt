package com.example.mobile_client_app.common.models

import com.example.mobile_client_app.common.formatDate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class DateRangePickerConfig(
    val allowPastDates: Boolean = true,
    val minDate: LocalDate? = null,
    val maxDate: LocalDate? = null,
    val maxRangeDays: Int? = null,
    val minRangeDays: Int? = null,
    val allowSingleDate: Boolean = true,
    val allowStartDateSelection: Boolean = true,
    val allowEndDateSelection: Boolean = true
){
    @OptIn(ExperimentalTime::class)
    fun validateDateSelection(selectedDate: LocalDate): String? {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

        if (!allowPastDates && selectedDate < today) {
            return "Past dates are not allowed"
        }

        minDate?.let { min ->
            if (selectedDate < min) {
                return "Date cannot be before ${formatDate(min)}"
            }
        }

        maxDate?.let { max ->
            if (selectedDate > max) {
                return "Date cannot be after ${formatDate(max)}"
            }
        }

        return null
    }

    fun validateDateRange(start: LocalDate, end: LocalDate): String? {
        if (!allowSingleDate && start == end) {
            return "Start and end dates cannot be the same"
        }

        val rangeDays = start.daysUntil(end).toInt() + 1

        minRangeDays?.let { min ->
            if (rangeDays < min) {
                return "Date range must be at least $min days"
            }
        }

        maxRangeDays?.let { max ->
            if (rangeDays > max) {
                return "Date range cannot exceed $max days"
            }
        }

        return null
    }
}