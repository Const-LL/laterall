package ru.otus.otuskotlin.laterall.common

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

private val INSTANT_NONE = Instant.fromEpochMilliseconds(Long.MIN_VALUE)
val Instant.Companion.NONE
    get() = INSTANT_NONE


fun getTestInstant(timeString: String) : Instant
{
    val ldt: LocalDateTime = LocalDateTime.parse(timeString)
    return ldt.toInstant(TimeZone.UTC)
}