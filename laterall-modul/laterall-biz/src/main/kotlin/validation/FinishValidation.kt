package ru.otus.otuskotlin.laterall.biz.validation

import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker

fun ICorChainDsl<LtrlContext>.finishTaskValidation(title: String) = worker {
    this.title = title
    on { state == LtrlState.RUNNING }
    handle {
        taskValidated = taskValidating
    }
}

fun ICorChainDsl<LtrlContext>.finishTaskFilterValidation(title: String) = worker {
    this.title = title
    on { state == LtrlState.RUNNING }
    handle {
        taskFilterValidated = taskFilterValidating
    }
}
