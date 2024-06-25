package ru.otus.otuskotlin.laterall.biz.general

import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.common.models.LtrlWorkMode
import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.chain


fun ICorChainDsl<LtrlContext>.stubs(title: String, block: ICorChainDsl<LtrlContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == LtrlWorkMode.STUB && state == LtrlState.RUNNING }
}
