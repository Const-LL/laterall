package ru.otus.otuskotlin.laterall.biz.stubs

import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.helpers.fail
import ru.otus.otuskotlin.laterall.common.models.LtrlError
import ru.otus.otuskotlin.laterall.common.models.LtrlState

fun ICorChainDsl<LtrlContext>.stubNoCase(title: String) = worker {
    this.title = title
    this.description = """
        Валидируем ситуацию, когда запрошен кейс, который не поддерживается в стабах
    """.trimIndent()
    on { state == LtrlState.RUNNING }
    handle {
        fail(
            LtrlError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}
