package ru.otus.otuskotlin.laterall.biz.stubs

import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.common.models.LtrlVisibility
import ru.otus.otuskotlin.laterall.common.stubs.LtrlStubs
import ru.otus.otuskotlin.laterall.stubs.LtrlTaskStub
import kotlin.time.measureTimedValue

fun ICorChainDsl<LtrlContext>.stubCreateSuccess(title: String, corSettings: LtrlCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для создания задачи
    """.trimIndent()

    on { stubCase == LtrlStubs.SUCCESS && state == LtrlState.RUNNING }
    handle {
        state = LtrlState.FINISHING
        val stub = LtrlTaskStub.prepareResult {
            taskRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
            taskRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            taskRequest.visibility.takeIf { it != LtrlVisibility.NONE }?.also { this.visibility = it }
        }
        taskResponse = stub
    }
}