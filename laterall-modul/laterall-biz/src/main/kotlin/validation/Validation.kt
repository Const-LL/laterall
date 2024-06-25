package ru.otus.otuskotlin.laterall.biz.validation

import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.chain

fun ICorChainDsl<LtrlContext>.validation(block: ICorChainDsl<LtrlContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == LtrlState.RUNNING }
}
