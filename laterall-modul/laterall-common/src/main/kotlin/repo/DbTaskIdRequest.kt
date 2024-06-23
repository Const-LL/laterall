package ru.otus.otuskotlin.laterall.common.repo

import ru.otus.otuskotlin.laterall.common.models.LtrlTask
import ru.otus.otuskotlin.laterall.common.models.LtrlTaskId

data class DbTaskIdRequest(
    val id: LtrlTaskId,
) {
    constructor(task: LtrlTask): this(task.id)
}
