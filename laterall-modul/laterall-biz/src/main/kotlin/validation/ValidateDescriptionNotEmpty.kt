package ru.otus.otuskotlin.laterall.biz.validation

import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker
import ru.otus.otuskotlin.laterall.common.helpers.errorValidation
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.helpers.fail

fun ICorChainDsl<LtrlContext>.validateDescriptionNotEmpty(title: String) = worker {
    this.title = title
    on { taskValidating.description.isEmpty() }
    handle {
        fail(
            errorValidation(
            field = "description",
            violationCode = "empty",
            description = "field must not be empty"
        )
        )
    }
}
