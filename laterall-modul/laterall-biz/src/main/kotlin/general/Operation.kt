package ru.otus.otuskotlin.laterall.biz.general

import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.LtrlCommand
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.chain

fun ICorChainDsl<LtrlContext>.operation(
    title: String,
    command: LtrlCommand,
    block: ICorChainDsl<LtrlContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == LtrlState.RUNNING }
}
