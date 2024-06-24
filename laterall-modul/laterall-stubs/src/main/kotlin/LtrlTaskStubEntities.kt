package ru.otus.otuskotlin.laterall.stubs

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.laterall.common.*
import ru.otus.otuskotlin.laterall.common.models.*

object LtrlTaskStubEntities {
    val TASK_CAR_ENTITY1: LtrlTask
        get() = LtrlTask(
            id = LtrlTaskId("123"),
            title = "Ремонт машины",
            description = "Необходимо купить запчасти по списку из сервиса",
            importance = LtrlTaskImportance.MEDIUM,
            priority = 50,
            taskstart = getTestInstant("2024-07-01T20:21:22"),
            taskend = getTestInstant("2024-07-02T20:21:22"),
            taskappend = getTestInstant("2024-07-01T20:00:22"),
            group = LtrlTaskGroup.CAR,
            ownerId = LtrlUserId("test_user_1"),
            visibility = LtrlVisibility.NONE,
            lock = LtrlTaskLock("123"),//todo expand lock?
            permissionsClient = mutableSetOf(
                LtrlTaskPermissionClient.READ,
                LtrlTaskPermissionClient.UPDATE,
                LtrlTaskPermissionClient.DELETE,
                LtrlTaskPermissionClient.MAKE_VISIBLE_PUBLIC,
                LtrlTaskPermissionClient.MAKE_VISIBLE_GROUP,
                LtrlTaskPermissionClient.MAKE_VISIBLE_OWNER,
            )
        )
}