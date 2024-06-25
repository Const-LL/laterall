package ru.otus.otuskotlin.laterall.biz.repo

import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker

fun ICorChainDsl<LtrlContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = """
        Готовим данные к удалению из БД
    """.trimIndent()
    on { state == LtrlState.RUNNING }
    handle {
        taskRepoPrepare = taskValidated.deepCopy()
    }
}
