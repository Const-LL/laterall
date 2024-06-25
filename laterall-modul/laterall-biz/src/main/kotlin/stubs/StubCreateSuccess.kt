package ru.otus.otuskotlin.laterall.biz.stubs

import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker
import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.LtrlCorSettings
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.common.models.LtrlTaskImportance
import ru.otus.otuskotlin.laterall.common.models.LtrlVisibility
import ru.otus.otuskotlin.laterall.common.models.LtrlTaskGroup
import ru.otus.otuskotlin.laterall.common.stubs.LtrlStubs
import ru.otus.otuskotlin.laterall.stubs.LtrlTaskStub
import kotlinx.datetime.Instant
import ru.otus.otuskotlin.laterall.common.NONE

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
            taskRequest.importance.takeIf { it != LtrlTaskImportance.NONE }?.also { this.importance = it }
            taskRequest.priority.takeIf { it != 0 }?.also { this.priority = it }
            taskRequest.group.takeIf { it != LtrlTaskGroup.NONE }?.also { this.group = it }
            taskRequest.taskstart.takeIf { it != Instant.NONE }?.also { this.taskstart = it }
            taskRequest.taskend.takeIf { it != Instant.NONE }?.also { this.taskend = it }
            taskRequest.taskappend.takeIf { it != Instant.NONE }?.also { this.taskappend = it }
        }
        taskResponse = stub
    }
}