package ru.otus.otuskotlin.laterall.biz.stubs

import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.helpers.fail
import ru.otus.otuskotlin.laterall.common.models.LtrlError
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.common.stubs.LtrlStubs

fun ICorChainDsl<LtrlContext>.stubDbError(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки базы данных
    """.trimIndent()
    on { stubCase == LtrlStubs.DB_ERROR && state == LtrlState.RUNNING }
    handle {
        fail(
            LtrlError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}
