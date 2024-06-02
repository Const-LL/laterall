package ru.otus.otuskotlin.laterall.common.helpers

import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.LtrlError
import ru.otus.otuskotlin.laterall.common.models.LtrlState

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


inline fun LtrlContext.addError(vararg error: LtrlError) = errors.addAll(error)

inline fun LtrlContext.fail(error: LtrlError) {
    addError(error)
    state = LtrlState.FAILING
}