package ru.otus.otuskotlin.laterall.repo.tests

import ru.otus.otuskotlin.laterall.common.models.*

abstract class BaseInitTasks(private val op: String): IInitObjects<LtrlTask> {
    open val lockOld: LtrlTaskLock = LtrlTaskLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: LtrlTaskLock = LtrlTaskLock("20000000-0000-0000-0000-000000000009")
    fun createInitTestModel(
        suf: String,
        ownerId: LtrlUserId = LtrlUserId("owner-123"),
        lock: LtrlTaskLock = lockOld
    ) = LtrlTask(
        id = LtrlTaskId("task-repo-$op-$suf"),
        title = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        visibility = LtrlVisibility.VISIBLE_TO_OWNER,
        lock = lock
    )
}
