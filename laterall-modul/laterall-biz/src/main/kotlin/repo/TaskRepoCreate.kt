package ru.otus.otuskotlin.laterall.biz.repo

import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.helpers.fail
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.common.repo.DbTaskRequest
import ru.otus.otuskotlin.laterall.common.repo.DbTaskResponseErr
import ru.otus.otuskotlin.laterall.common.repo.DbTaskResponseErrWithData
import ru.otus.otuskotlin.laterall.common.repo.DbTaskResponseOk
import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker

fun ICorChainDsl<LtrlContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление задачи в БД"
    on { state == LtrlState.RUNNING }
    handle {
        val request = DbTaskRequest(taskRepoPrepare)
        when(val result = taskRepo.createTask(request)) {
            is DbTaskResponseOk -> taskRepoDone = result.data
            is DbTaskResponseErr -> fail(result.errors)
            is DbTaskResponseErrWithData -> fail(result.errors)
        }
    }
}
