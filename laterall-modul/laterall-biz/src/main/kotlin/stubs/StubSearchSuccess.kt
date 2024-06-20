package ru.otus.otuskotlin.laterall.biz.stubs

import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings
import ru.otus.otuskotlin.laterall.common.models.*
import ru.otus.otuskotlin.laterall.common.stubs.LtrlStubs
import ru.otus.otuskotlin.laterall.stubs.LtrlTaskStub


fun ICorChainDsl<LtrlContext>.stubSearchSuccess(title: String, corSettings: LtrlCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для поиска объявлений
    """.trimIndent()
    on { stubCase == LtrlStubs.SUCCESS && state == LtrlState.RUNNING }
    handle {
        state = LtrlState.FINISHING
        tasksResponse.addAll(LtrlTaskStub.prepareCarTaskList(taskFilterRequest.searchString))
    }
}
