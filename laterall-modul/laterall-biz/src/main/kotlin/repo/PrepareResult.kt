package ru.otus.otuskotlin.laterall.biz.repo

import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.common.models.LtrlWorkMode
import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker

fun ICorChainDsl<LtrlContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != LtrlWorkMode.STUB }
    handle {
        taskResponse = taskRepoDone
        tasksResponse = tasksRepoDone
        state = when (val st = state) {
            LtrlState.RUNNING -> LtrlState.FINISHING
            else -> st
        }
    }
}
