package ru.otus.otuskotlin.laterall.biz.validation

import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.helpers.errorValidation
import ru.otus.otuskotlin.laterall.common.helpers.fail
import ru.otus.otuskotlin.laterall.common.models.LtrlTaskLock
import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker

fun ICorChainDsl<LtrlContext>.validateLockProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в LtrlTaskId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { taskValidating.lock != LtrlTaskLock.NONE && !taskValidating.lock.asString().matches(regExp) }
    handle {
        val encodedId = taskValidating.lock.asString()
        fail(
            errorValidation(
                field = "lock",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}
