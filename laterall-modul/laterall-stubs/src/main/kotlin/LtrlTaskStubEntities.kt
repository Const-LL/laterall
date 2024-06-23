package ru.otus.otuskotlin.laterall.stubs

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.laterall.common.NONE
import ru.otus.otuskotlin.laterall.common.models.*

object LtrlTaskStubEntities {
    val TASK_CAR_ENTITY1: LtrlTask
        get() = LtrlTask(
            id = LtrlTaskId("123"),
            title = "Ремонт машины",
            description = "Необходимо купить запчасти по списку из сервиса",
            importance = LtrlTaskImportance.MEDIUM,
            priority = 50,
            taskstart = Instant.NONE, //todo
            taskend = Instant.NONE, //todo
            taskappend = Instant.NONE, //todo
            group = LtrlTaskGroup.CAR,
            ownerId = LtrlUserId("test_user_1"),
            visibility = LtrlVisibility.NONE,
            lock = LtrlTaskLock("123"),
            permissionsClient = mutableSetOf(
                LtrlTaskPermissionClient.READ,
                LtrlTaskPermissionClient.UPDATE,
                LtrlTaskPermissionClient.DELETE,
                LtrlTaskPermissionClient.MAKE_VISIBLE_PUBLIC,
                LtrlTaskPermissionClient.MAKE_VISIBLE_GROUP,
                LtrlTaskPermissionClient.MAKE_VISIBLE_OWNER,
            )
        )
//    val TASK_HOME_ENTITY1: LtrlTask
//        get() = LtrlTask(
//            id = LtrlTaskId("1234"),
//            title = "Поход в театр",
//            description = "Семьей сходить на спектакль",
//            importance = LtrlTaskImportance.HIGH,
//            priority = 100,
//            taskstart = Instant.NONE, //todo
//            taskend = Instant.NONE, //todo
//            taskappend = Instant.NONE, //todo
//            group = LtrlTaskGroup.HOME,
//            ownerId = LtrlUserId("test_user_2"),
//            visibility = LtrlVisibility.VISIBLE_TO_GROUP,
//            permissionsClient = mutableSetOf(
//                LtrlTaskPermissionClient.READ,
//                LtrlTaskPermissionClient.UPDATE,
//                LtrlTaskPermissionClient.DELETE,
//                LtrlTaskPermissionClient.MAKE_VISIBLE_PUBLIC,
//                LtrlTaskPermissionClient.MAKE_VISIBLE_GROUP,
//                LtrlTaskPermissionClient.MAKE_VISIBLE_OWNER,
//            )
//        )
}