package ru.otus.otuskotlin.laterall.biz.repo

import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.common.models.LtrlUserId
import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker

fun ICorChainDsl<LtrlContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == LtrlState.RUNNING }
    handle {
        taskRepoPrepare = taskValidated.deepCopy()
        // TODO будет реализовано в занятии по управлению пользвателями
        taskRepoPrepare.ownerId = LtrlUserId.NONE
    }
}
