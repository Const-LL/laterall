package ru.otus.otuskotlin.laterall.biz.repo

import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.helpers.fail
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.common.repo.DbTaskIdRequest
import ru.otus.otuskotlin.laterall.common.repo.DbTaskResponseErr
import ru.otus.otuskotlin.laterall.common.repo.DbTaskResponseErrWithData
import ru.otus.otuskotlin.laterall.common.repo.DbTaskResponseOk
import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker

fun ICorChainDsl<LtrlContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение задачи из БД"
    on { state == LtrlState.RUNNING }
    handle {
        val request = DbTaskIdRequest(taskValidated)
        when(val result = taskRepo.readTask(request)) {
            is DbTaskResponseOk -> taskRepoRead = result.data
            is DbTaskResponseErr -> fail(result.errors)
            is DbTaskResponseErrWithData -> {
                fail(result.errors)
                taskRepoRead = result.data
            }
        }
    }
}
