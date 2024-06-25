package ru.otus.otuskotlin.laterall.common

import kotlinx.datetime.*
import kotlinx.datetime.format.DateTimeComponents

private val INSTANT_NONE = Instant.fromEpochMilliseconds(Long.MIN_VALUE)
val Instant.Companion.NONE
    get() = INSTANT_NONE


fun getTestInstant(timeString: String) : Instant
{
    val ldt: LocalDateTime = LocalDateTime.parse(timeString)
    return ldt.toInstant(TimeZone.UTC)
}

fun getInstant(timeString: String) : Instant
{
    val customFormat = DateTimeComponents.Format {
        dateTime(LocalDateTime.Formats.ISO)
        offset(UtcOffset.Formats.ISO)
    }
    val ldt: LocalDateTime = customFormat.parse(timeString).toLocalDateTime();
    return ldt.toInstant(TimeZone.UTC)
}