package ru.otus.otuskotlin.laterall.biz.validation

import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker
import ru.otus.otuskotlin.laterall.common.helpers.errorValidation
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.helpers.fail
import ru.otus.otuskotlin.laterall.common.models.LtrlTaskId

fun ICorChainDsl<LtrlContext>.validateIdProperFormat(title: String) = worker {
    this.title = title

    // Может быть вынесен в LtrlTaskId для реализации различных форматов
    val regExp = Regex("^[0-9a-zA-Z#:-]+$")
    on { taskValidating.id != LtrlTaskId.NONE && ! taskValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = taskValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
            field = "id",
            violationCode = "badFormat",
            description = "value $encodedId must contain only letters and numbers"
        )
        )
    }
}
