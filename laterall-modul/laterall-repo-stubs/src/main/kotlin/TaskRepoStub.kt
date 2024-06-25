package ru.otus.otuskotlin.laterall.repo.stubs

import ru.otus.otuskotlin.laterall.common.repo.*
import ru.otus.otuskotlin.laterall.stubs.LtrlTaskStub

class TaskRepoStub() : IRepoTask {
    override suspend fun createTask(rq: DbTaskRequest): IDbTaskResponse {
        return DbTaskResponseOk(
            data = LtrlTaskStub.get(),
        )
    }

    override suspend fun readTask(rq: DbTaskIdRequest): IDbTaskResponse {
        return DbTaskResponseOk(
            data = LtrlTaskStub.get(),
        )
    }

    override suspend fun updateTask(rq: DbTaskRequest): IDbTaskResponse {
        return DbTaskResponseOk(
            data = LtrlTaskStub.get(),
        )
    }

    override suspend fun deleteTask(rq: DbTaskIdRequest): IDbTaskResponse {
        return DbTaskResponseOk(
            data = LtrlTaskStub.get(),
        )
    }

    override suspend fun searchTask(rq: DbTaskFilterRequest): IDbTasksResponse {
        return DbTasksResponseOk(
            data = LtrlTaskStub.prepareCarTaskList(filter = ""),
        )
    }
}
