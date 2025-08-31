package com.example.mobile_client_app.common.component

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import saschpe.log4k.Log
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun millisToDate(millis: Long): LocalDate {
    val instant = Instant.fromEpochMilliseconds(millis)
    val datetime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    Log.debug { "Datetime is: $datetime" }
    return datetime.date
}

fun LocalDateTime?.toDDMMYYY(): String? {
    return if (this == null) null else this.dayOfMonth.pad(2) +
            "/${this.month.number.pad(2)}/" +
            this.year.pad(2)
}

fun Int.pad(digits: Int) = this.toString().padStart(digits, '0')