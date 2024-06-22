package ru.otus.otuskotlin.laterall.biz.validation

import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker
import ru.otus.otuskotlin.laterall.common.helpers.errorValidation
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.helpers.fail

// смотрим пример COR DSL валидации
fun ICorChainDsl<LtrlContext>.validateTitleNotEmpty(title: String) = worker {
    this.title = title
    on { taskValidating.title.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "title",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
