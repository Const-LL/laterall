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

inline fun LtrlContext.addError(error: LtrlError) = errors.add(error)
inline fun LtrlContext.addErrors(error: Collection<LtrlError>) = errors.addAll(error)

inline fun LtrlContext.fail(error: LtrlError) {
    addError(error)
    state = LtrlState.FAILING
}

inline fun LtrlContext.fail(errors: Collection<LtrlError>) {
    addErrors(errors)
    state = LtrlState.FAILING
}

inline fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String
) = LtrlError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
)

inline fun errorSystem(
    violationCode: String,
    e: Throwable,
) = LtrlError(
    code = "system-$violationCode",
    group = "system",
    message = "System error occurred. Our stuff has been informed, please retry later",
    exception = e,
)
