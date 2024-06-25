package ru.otus.otuskotlin.laterall.common.repo

import ru.otus.otuskotlin.laterall.common.models.LtrlTask
import ru.otus.otuskotlin.laterall.common.models.LtrlTaskId
import ru.otus.otuskotlin.laterall.common.models.LtrlTaskLock

data class DbTaskIdRequest(
    val id: LtrlTaskId,
    val lock: LtrlTaskLock = LtrlTaskLock.NONE,
) {
    constructor(task: LtrlTask): this(task.id, task.lock)
}
