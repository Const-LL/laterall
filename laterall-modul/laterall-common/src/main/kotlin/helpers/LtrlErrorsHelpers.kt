package ru.otus.otuskotlin.laterall.common.helpers

import ru.otus.otuskotlin.laterall.common.models.LtrlError

fun Throwable.asLtrlError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = LtrlError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)
