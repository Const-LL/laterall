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

fun ICorChainDsl<LtrlContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление задачи из БД по ID"
    on { state == LtrlState.RUNNING }
    handle {
        val request = DbTaskIdRequest(taskRepoPrepare)
        when(val result = taskRepo.deleteTask(request)) {
            is DbTaskResponseOk -> taskRepoDone = result.data
            is DbTaskResponseErr -> {
                fail(result.errors)
                taskRepoDone = taskRepoRead
            }
            is DbTaskResponseErrWithData -> {
                fail(result.errors)
                taskRepoDone = result.data
            }
        }
    }
}
