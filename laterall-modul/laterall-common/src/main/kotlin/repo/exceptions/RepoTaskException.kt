package ru.otus.otuskotlin.laterall.common.repo.exceptions

import ru.otus.otuskotlin.laterall.common.models.LtrlTaskId

open class RepoTaskException(
    @Suppress("unused")
    val taskId: LtrlTaskId,
    msg: String,
): RepoException(msg)
