package com.example.mobile_client_app.common.component

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import saschpe.log4k.Log
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun millisToDate(millis: Long): LocalDateTime {
    val instant = Instant.fromEpochMilliseconds(millis)
    val datetime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    Log.debug { "Datetime is: $datetime" }
    return datetime
}

fun LocalDateTime?.toDDMMYYY(): String? {
    return if (this == null) null else this.day.pad(2) +
            "/${this.month.number.pad(2)}/" +
            this.year.pad(2)
}

fun Int.pad(digits: Int) = this.toString().padStart(digits, '0')