package ru.otus.otuskotlin.laterall.repo.tests

import ru.otus.otuskotlin.laterall.common.models.*

abstract class BaseInitTasks(private val op: String): IInitObjects<LtrlTask> {
    fun createInitTestModel(
        suf: String,
        ownerId: LtrlUserId = LtrlUserId("owner-123"),
    ) = LtrlTask(
        id = LtrlTaskId("ad-repo-$op-$suf"),
        title = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        visibility = LtrlVisibility.VISIBLE_TO_OWNER,
    )
}
