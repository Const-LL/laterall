package ru.otus.otuskotlin.laterall.biz.repo

import ru.otus.otuskotlin.laterall.common.LtrlContext
import ru.otus.otuskotlin.laterall.common.helpers.fail
import ru.otus.otuskotlin.laterall.common.models.LtrlState
import ru.otus.otuskotlin.laterall.common.repo.DbTaskFilterRequest
import ru.otus.otuskotlin.laterall.common.repo.DbTasksResponseErr
import ru.otus.otuskotlin.laterall.common.repo.DbTasksResponseOk
import ru.otus.otuskotlin.laterall.cor.ICorChainDsl
import ru.otus.otuskotlin.laterall.cor.worker

fun ICorChainDsl<LtrlContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск задач в БД по фильтру"
    on { state == LtrlState.RUNNING }
    handle {
        val request = DbTaskFilterRequest(
            titleFilter = taskFilterValidated.searchString,
            ownerId = taskFilterValidated.ownerId,
        )
        when(val result = taskRepo.searchTask(request)) {
            is DbTasksResponseOk -> tasksRepoDone = result.data.toMutableList()
            is DbTasksResponseErr -> fail(result.errors)
        }
    }
}
